(ns dnd-memo.routes.home
  (:require [dnd-memo.layout :as layout]
            [dnd-memo.util :as util]
            [compojure.core :refer :all]
            [noir.response :refer [edn]]
            [clj-rss.core :as rss]
            [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.pprint :refer [pprint]]))

(defn home-page []
      (layout/render
        "home.html" {:content (util/md->html "/md/docs.md")}))

(defn save-document [doc]
      (pprint doc)
      {:status "ok"})

(defn rss-page []
  (rss/channel-xml {:title "Foo" :link "http://foo/bar" :description "some channel"}
                   {:title "Foo"}
                   {:title "post" :author "author@foo.bar"}
                   {:description "bar"})
)

(defn pocket-page []
  (let [pocket-json (client/get "https://getpocket.com/v3/get?consumer_key=30350-8051b21ac3079efa21ee9561&access_token=f1ea25c5-c242-17de-f59b-a9645b&count=1&detailType=complete")
        pocket-content (json/read-json (:body pocket-json))]
    (rss/channel-xml {:title "Foo" :link "http://foo/bar" :description (-> pocket-content :list :9970250 :excerpt)}
                     {:description "DnD review for today"})))
  

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/rss" [] (rss-page))
  (GET "/pocket" [] (pocket-page))
  (POST "/save" {:keys [body-params]}
    (edn (save-document body-params))))
