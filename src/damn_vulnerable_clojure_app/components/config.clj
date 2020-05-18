(ns damn-vulnerable-clojure-app.components.config
  (:require [com.stuartsierra.component :as component]))

(defrecord Config [connection]
  component/Lifecycle

  (start [this]
    (assoc this :config {}))

  (stop [this]
    (.close connection)
    (assoc this :config nil)))

(defn new-config [] (map->Config {}))
