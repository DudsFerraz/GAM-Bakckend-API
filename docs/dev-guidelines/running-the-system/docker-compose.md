# Docker Compose

Use these commands from the repository root.

## PostgreSQL Commands

Start PostgreSQL in the background:

```powershell
docker compose up -d postgres
```

Show service status:

```powershell
docker compose ps
```

Follow PostgreSQL logs:

```powershell
docker compose logs -f postgres
```

Stop the database while keeping the volume:

```powershell
docker compose stop
```

Stop and remove the container while keeping the volume:

```powershell
docker compose down
```

Reset the local database by removing the container and volume:

```powershell
docker compose down -v
```

Use `down -v` only when you intentionally want to delete local database data.

Restart PostgreSQL:

```powershell
docker compose restart postgres
```

Pull newer service images:

```powershell
docker compose pull
```

## Local Database Details

The local PostgreSQL service is defined in `compose.yml`.

- Host port: `5433`
- Container port: `5432`
- Database: `gam_api`
- User: `gam`
- Password: `gam_dev_password`

Use these values only for local development.
