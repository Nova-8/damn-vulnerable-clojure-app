(ns damn-vulnerable-clojure-app.components.server
  (:require [io.pedestal.http :as server]
    [io.pedestal.http.route :as route]
    [damn-vulnerable-clojure-app.service :as service]
    [com.stuartsierra.component :as component]))

(defonce runnable-service (server/create-server service/service))

(defrecord Server [connection]
  component/Lifecycle

  (start [this]
    (assoc this :server (server/start runnable-service)))

  (stop [this]
    (server/stop (:server this))
    (.close connection)
    (assoc this :server nil)))

(defn start-server [] (map->Server {}))
