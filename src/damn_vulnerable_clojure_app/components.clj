(ns damn-vulnerable-clojure-app.components
  (:require
   [damn-vulnerable-clojure-app.components.config :as config]
   [damn-vulnerable-clojure-app.components.database :as database]
   [damn-vulnerable-clojure-app.components.server :as server]
   [com.stuartsierra.component :as component]))

(defn base []
  (component/system-map
   :config (config/new-config)
   :database (component/using (database/new-database) [:config])
   :server (component/using (server/start-server) [:database])))
