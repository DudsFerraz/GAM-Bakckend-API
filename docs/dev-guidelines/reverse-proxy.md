# Reverse Proxy

GAM exposes one browser origin. The proxy serves the frontend and forwards `/api/` to the backend.

```text
Browser -> https://gam.org.br/       -> frontend files
Browser -> https://gam.org.br/api/   -> backend:8080
                                      -> database:5432
```

This keeps frontend requests same-origin, terminates HTTPS, and keeps backend and database ports private. The proxy is not an application security boundary: the backend still authenticates, authorizes, validates input, and enforces business rules.

## Routing

```text
/api/* -> backend:8080
/*     -> frontend static directory
```

Unknown frontend routes such as `/members/123` should fall back to `index.html`. `/api/*` must never use that fallback.

Preserve the method, path, query string, relevant headers, cookies, and response status. Forward the external request context:

```http
Host: gam.org.br
X-Forwarded-Proto: https
X-Forwarded-For: <client-address>
```

Trust forwarded headers only from the proxy network. Do not trust values supplied directly by public clients.

## HTTPS and cookies

The public hop must use HTTPS. Redirect HTTP to HTTPS, renew certificates, and preserve cookie attributes such as `HttpOnly`, `Secure`, `SameSite`, and `Path`.

Do not remove or rewrite security-sensitive cookie attributes without a documented, tested reason. Do not log access tokens or refresh tokens.

## Development

Use the frontend development proxy to keep the browser-facing shape consistent with production:

```text
Browser:          http://localhost:5173/api
Development proxy: http://localhost:8080
```

If development uses separate origins, configure CORS and cookies explicitly. Do not copy that setup to production without a deliberate architecture decision.

## Deployment checklist

- `https://gam.org.br/` serves the frontend.
- `/api/...` reaches only the backend.
- Frontend route fallback does not affect API errors.
- Backend and database ports are not publicly exposed.
- Forwarded headers are set and trusted only from the proxy.
- HTTPS, certificate renewal, limits, timeouts, and logs are configured.
- Proxy logs do not contain credentials.
- Configuration rollback and recovery have been tested.

See [Web Delivery and Frontend Contract](../requirements/platform/web-delivery-and-frontend-contract.md) and [ADR-0006](../decisions/0006-use-a-single-vps-same-origin-proxy-topology.md) for the owning decisions.
