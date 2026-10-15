# ecommerce-monorepo
A full-featured, scalable eCommerce platform built using Java, Spring Boot, and modern cloud-native architecture. Designed to demonstrate expertise in backend development, microservices, DevOps, and secure system design.

## Authentication (JWT)

Every API is behind a JWT layer. Only these calls work without a token:

| Through the gateway (port 8080) | What it does |
|---|---|
| `POST /user/api` | Register (creates the user, then its login in auth-service) |
| `POST /auth/api/login` | `{"username","password"}` → access token (15 min) + refresh token (7 days) |
| `POST /auth/api/refresh` | `{"refreshToken"}` → new token pair; the old refresh token is revoked |
| `POST /auth/api/logout` | `{"refreshToken"}` → revokes it |

Send the access token on every other call as `Authorization: Bearer <token>`. The gateway checks it, and every service checks it again, so calling a service directly without a valid token returns 401.

How it fits together:
- `services/ecommerce-security-common` is a shared library each service depends on. It locks every endpoint except `ecommerce.security.public-paths`, validates the token locally, and forwards the caller's token on Feign/WebClient calls (or a short-lived service token for background work).
- `auth-service` checks passwords (BCrypt) and issues the tokens. `/auth/create` and `/auth/update` only accept service tokens.
- Use `@PreAuthorize("hasRole('ADMIN')")` on any endpoint that needs a role. The logged-in user is available as `@AuthenticationPrincipal AuthenticatedUser`.

**Setup:** every service (and the gateway) needs the same signing key in the `JWT_SECRET` environment variable, at least 32 characters, for example `export JWT_SECRET=$(openssl rand -base64 48)`. Services refuse to start without it. Never commit it.

**Build:** run `mvn install` from the repository root; it builds the shared library before the services.
