(ns dnd-memo-scheduler.routes.home
  (:require [compojure.core :refer :all]
            [dnd-memo-scheduler.layout :as layout]
            [dnd-memo-scheduler.util :as util]
            [clj-rss.core :as rss]))

(defn home-page []
  (layout/render
    "home.html" {:content (util/md->html "/md/docs.md")}))

(defn about-page []
  (layout/render "about.html"))

(defn rss-page []
  (rss/channel-xml {:title "Foo" :link "http://foo/bar" :description "some channel"}
                 {:title "Foo"}
                 {:title "post" :author "author@foo.bar"}
                 {:description "bar"})
)

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/rss" [] (rss-page))
  (GET "/about" [] (about-page)))
