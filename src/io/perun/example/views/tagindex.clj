(ns io.perun.example.views.tagindex
  (:require
   [hiccup.page :refer [html5]]
   [beautify-web.core :as bw]
   [io.perun.example.core :as core]))
(defn- tags-from-posts
  [posts]
  (remove nil?
  (->> posts
               (map #(get % :tags))
               (flatten)
               (distinct))))
(defn render [{meta :meta posts :entries}]
  (bw/beautify-html
  (html5 {:lang "en" :itemtype "http://schema.org/Blog"}
         (core/head meta "Tags" :menuh1)
         [:body
          (core/header meta {:site-title "Tags" :active :tags :h1-menu-item :tags :logo :site})
          [:main {:role "main"}
           [:article
            [:div.row
             [:div.columns
              [:ul {:style "list-style:none;"}
               (for [tag (tags-from-posts posts)]
                 (if true [:li [:a {:style "color:#654; text-decoration: none;",:href (str tag ".html")} (str "#" tag)]]))]]]]]]
         (core/scripts))))
