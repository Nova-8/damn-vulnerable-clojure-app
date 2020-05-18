(ns damn-vulnerable-clojure-app.service-test
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [io.pedestal.test :refer :all]
            [io.pedestal.http :as bootstrap]
            [damn-vulnerable-clojure-app.service :as service]))

(def service
  (::bootstrap/service-fn (bootstrap/create-servlet service/service)))

(facts "about health endpoint"
  (fact "it should return 200 and a empty body"
    (let [response (response-for service :get "/health")]
      (:body response) => "{}"
      (:status response) => 200)))


(facts "about graph endpoint"
  (fact "it should return 200 and a empty body when given a empty query")
  (let [response (response-for service :post "/api/graph" :body "{\"query\": \"a\"}")]
    (:body response) => "{}"
    (:status response) => 200))
