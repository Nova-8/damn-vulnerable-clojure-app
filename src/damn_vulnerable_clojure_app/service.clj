(ns damn-vulnerable-clojure-app.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :refer [expand-routes]]
            [com.walmartlabs.lacinia.schema :as schema]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]))

(defn health
  [_]
  (ring-resp/response {}))

(defn graphql-query
  [request]
  (ring-resp/response {}))

(def example-schema (schema/compile
                   {:queries {:example
                              {:type 'String
                               :resolve (constantly "worked")}}}))

(def common-interceptors [(body-params/body-params) http/html-body])

(def routes
  (expand-routes
   `[[["/" ^:interceptors common-interceptors
       ["/health" ^:interceptors []
        {:get health}]
       ["/api"
        ["/graph" ^:interceptors []
         {:post graphql-query}]]]]]))

;; Consumed by damn-vulnerable-clojure-app.server/create-server
;; See http/default-interceptors for additional options you can configure
(def service {:env :prod
              ::http/routes routes
              ::http/resource-path "/public"
              ::http/type :jetty
              ::http/port 8080
              ::http/container-options {:h2c? true
                                        :h2? false
                                        :ssl? false
                                        }})
