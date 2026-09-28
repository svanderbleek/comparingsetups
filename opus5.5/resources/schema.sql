CREATE TABLE IF NOT EXISTS items (
  id          SERIAL PRIMARY KEY,
  name        TEXT NOT NULL,
  description TEXT,
  created_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

INSERT INTO items (name, description)
SELECT * FROM (VALUES
  ('Clojure',    'A dynamic, functional Lisp on the JVM'),
  ('PostgreSQL', 'The relational database behind this list'),
  ('re-frame',   'A ClojureScript framework for building UIs'),
  ('shadow-cljs','ClojureScript compilation made easy')
) AS seed(name, description)
WHERE NOT EXISTS (SELECT 1 FROM items);
