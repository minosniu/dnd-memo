(ns dnd-memo.routes.home
  (:require [dnd-memo.layout :as layout]
            [dnd-memo.util :as util]
            [compojure.core :refer :all]
            [noir.response :refer [edn]]
            [clojure.pprint :refer [pprint]]))

(defn home-page []
      (layout/render
        "home.html" {:content (util/md->html "/md/docs.md")}))

(defn save-document [doc]
      (pprint doc)
      {:status "ok"})

(defroutes home-routes
  (GET "/" [] (home-page))
  (POST "/save" {:keys [body-params]}
    (edn (save-document body-params))))
