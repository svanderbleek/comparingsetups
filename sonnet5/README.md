# myapp

Full-stack Clojure/ClojureScript example:

- **Backend** (`src/backend`): Ring + Jetty + Reitit, connects to Postgres via `next.jdbc`, built into an uberjar with `tools.build`.
- **Frontend** (`src/frontend`): re-frame + Reagent, built with shadow-cljs.
- The index page fetches `GET /api/items` and renders the rows of the `items` table.

## Prerequisites

- JDK 11+
- [Clojure CLI](https://clojure.org/guides/install_clojure)
- Node.js 18+ / npm
- Docker (for local Postgres), or an existing Postgres instance

## 1. Start Postgres

```bash
docker compose up -d
```

This starts Postgres on `localhost:5432` (db `myapp`, user/pass `myapp`/`myapp`) and seeds it from `sql/init.sql`, which creates the `items` table with a few sample rows.

If you're pointing at your own Postgres instead, run `sql/init.sql` against it and set `PGHOST` / `PGPORT` / `PGDATABASE` / `PGUSER` / `PGPASSWORD` env vars before starting the backend (defaults match the docker-compose values).

## 2. Install frontend deps

```bash
npm install
```

## 3. Run in development

In one terminal, start the backend (serves the API on port 3000):

```bash
clojure -M:run
```

In another terminal, start the shadow-cljs dev server (serves the frontend on port 8280 and proxies `/api/*` to the backend):

```bash
npm run watch
```

Open http://localhost:8280 — it lists the rows from the `items` table.

## 4. Production build

```bash
npm run release          # compiles ClojureScript into resources/public/js
clojure -T:build uber    # builds target/server-standalone.jar (bundles resources/public)
java -jar target/server-standalone.jar
```

The backend now serves both the API and the compiled frontend on a single port (`3000` by default, override with `PORT`).
