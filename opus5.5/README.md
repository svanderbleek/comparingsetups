# app

A Clojure web server (Ring + Reitit + next.jdbc/Postgres) with a re-frame frontend
compiled by shadow-cljs, built with tools.build. The index page lists the rows of
the `items` table.

## Prerequisites

- Java 21+, Clojure CLI, Node.js
- A running PostgreSQL with a database: `createdb clj_app`

Connection settings come from env vars (defaults in parentheses):
`DB_HOST` (localhost), `DB_PORT` (5432), `DB_NAME` (clj_app),
`DB_USER` (your OS user), `DB_PASSWORD` (empty), and `PORT` (3000) for the web server.

On startup the server runs `resources/schema.sql`, which creates the `items`
table and seeds it if empty.

## Development

```sh
npm install
npx shadow-cljs watch app     # frontend with hot reload
clj -M:dev                    # REPL: (start) / (stop) / (restart)
# or simply: clj -M:run
```

Open http://localhost:3000.

## Production build

```sh
clj -T:build uber             # shadow-cljs release + AOT + uberjar
java -jar target/app-0.1.0-standalone.jar
```

## Layout

```
build.clj               tools.build tasks: clean, cljs, uber
deps.edn                server deps; :cljs alias used by shadow-cljs
shadow-cljs.edn         frontend build config
src/clj/app/            main.clj, server.clj (routes), db.clj (pool, queries)
src/cljs/app/           core, events, subs, views (re-frame)
resources/schema.sql    table + seed data
resources/public/       index.html, css, compiled js
dev/user.clj            REPL helpers
```
