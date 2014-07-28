(ns dnd-memo.routes.home
  (:require [dnd-memo.layout :as layout]
            [dnd-memo.util :as util]
            [compojure.core :refer :all]
            [noir.response :refer [edn]]
            [clj-rss.core :as rss]
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

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/rss" [] (rss-page))
  (POST "/save" {:keys [body-params]}
    (edn (save-document body-params))))
