const Database = require('better-sqlite3');

const db = new Database('./events.db');

db.exec(`
  CREATE TABLE IF NOT EXISTS events (
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    title        TEXT NOT NULL,
    description  TEXT,
    lat          REAL NOT NULL,
    lng          REAL NOT NULL,
    date         TEXT,
    category     TEXT,
    share_token  TEXT UNIQUE
  )
`);

module.exports = db;
