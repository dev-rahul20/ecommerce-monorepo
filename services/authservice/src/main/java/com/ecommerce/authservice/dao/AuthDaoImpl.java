package com.ecommerce.authservice.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ecommerce.authservice.entity.AuthUser;
import com.ecommerce.authservice.entity.RefreshToken;
import com.ecommerce.authservice.entity.Role;
import com.ecommerce.authservice.entity.UserRole;
import com.ecommerce.authservice.entity.UserRoleId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthDaoImpl implements AuthDao {

	private final SessionFactory factory;

	private Session getSession() {
		return factory.getCurrentSession();
	}

	@Override
	public Long createAuthUser(AuthUser authUser) {
		getSession().persist(authUser);
		return authUser.getAuthUserId();
	}

	@Override
	public Long updateAuthUser(AuthUser authUser) {
		getSession().merge(authUser);
		return authUser.getAuthUserId();
	}

	@Override
	public Optional<AuthUser> findByUsername(String username) {
		return getSession().createQuery("FROM AuthUser u WHERE u.username = :username", AuthUser.class)
						   .setParameter("username", username)
						   .uniqueResultOptional();
	}

	@Override
	public Optional<AuthUser> findById(Long authUserId) {
		return Optional.ofNullable(getSession().get(AuthUser.class, authUserId));
	}

	@Override
	public List<String> findRoleNames(Long authUserId) {
		return getSession().createQuery(
				"SELECT r.name FROM UserRole ur JOIN ur.role r WHERE ur.authUser.authUserId = :id", String.class)
						   .setParameter("id", authUserId)
						   .list();
	}

	@Override
	public void assignRole(AuthUser authUser, String roleName) {
		Session session = getSession();
		Role role = session.createQuery("FROM Role r WHERE r.name = :name", Role.class)
						   .setParameter("name", roleName)
						   .uniqueResultOptional()
						   .orElseGet(() -> {
							   Role created = Role.builder().name(roleName).description(roleName + " role").build();
							   session.persist(created);
							   return created;
						   });
		UserRoleId id = new UserRoleId(authUser.getAuthUserId(), role.getRoleId());
		if (session.get(UserRole.class, id) == null) {
			session.persist(UserRole.builder()
									.id(id)
									.authUser(authUser)
									.role(role)
									.assignedAt(LocalDateTime.now())
									.build());
		}
	}

	@Override
	public void saveRefreshToken(RefreshToken token) {
		getSession().persist(token);
	}

	@Override
	public Optional<RefreshToken> findRefreshTokenByHash(String tokenHash) {
		return getSession().createQuery("FROM RefreshToken t JOIN FETCH t.authUser WHERE t.tokenHash = :hash",
				RefreshToken.class).setParameter("hash", tokenHash).uniqueResultOptional();
	}

	@Override
	public void updateFailedAttempts(Long authUserId, int failedAttempts, boolean locked) {
		getSession().createMutationQuery(
				"UPDATE AuthUser u SET u.failedAttempts = :n, u.locked = :locked WHERE u.authUserId = :id")
					.setParameter("n", failedAttempts)
					.setParameter("locked", locked)
					.setParameter("id", authUserId)
					.executeUpdate();
	}

	@Override
	public int revokeAllRefreshTokens(Long authUserId) {
		return getSession().createMutationQuery(
				"UPDATE RefreshToken t SET t.revoked = true WHERE t.authUser.authUserId = :id AND t.revoked = false")
						   .setParameter("id", authUserId)
						   .executeUpdate();
	}
}
