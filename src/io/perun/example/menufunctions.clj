(ns io.perun.example.menufunctions
(:require
 [hiccup.page :refer [html5]]
 [clojure.string]))

(defn menu-item-view [li-name li-data]
  (if (keyword? (:name li-data))
    (cond
      (= li-name :home) (clojure.string/capitalize (name li-name))
      (= li-name :site) [:span {:style "color:#0084B4; font-family:\"Times New Roman\", Times, serif"} "Trzczy"])
    (:name li-data)))

(defn page [data]
  [:ul.dropdown.menu {:data-dropdown-menu "data-dropdown-menu" :role "menubar"}
   ;;for each menu item
   (for [n (range (count (first data)))]
     (let [li-data (nth (rest data) n)
           li-name (nth (first data) n)]
       [:li {:role "menuitem"} [:a
             (if (:active li-data)
               {:href (:href li-data) :title (:title li-data) :class "is-active"}
               {:href (:href li-data) :title (:title li-data)})
             (if (:h1wrap li-data) [:h1.menu (menu-item-view li-name li-data)] (menu-item-view li-name li-data))]]))])


;(cons base-list all-menu-data)
(defn sort-menu-data [data]
  (def l1 (atom ()))
  (doseq [item (first data)] (do (swap! l1 conj (item (into {} (rest data))))))
  (cons (first data) (reverse @l1))
  )



(defn regenerate-menu-data [data]
  (def l1 (atom ()))
  (doseq [item (first data)]
    (do
      (swap! l1 conj {item (item (into {} (rest data)))})))
  (reverse @l1))
(defn add-feature [kind-key menu-item-key data]
  (def l1 (atom (list)))
  (dotimes [n (count (first data))]
    (swap! l1
           conj
           (cond
             (= menu-item-key (nth (first data) n))
             (cond
               (= kind-key :h1-menu-item)
               (assoc (nth (rest data) n) :h1wrap true)
               (= :active kind-key)
               (assoc (nth (rest data) n) :active true)
               false
               "never do it"
               :else
               (assoc (nth (rest data) n) :nothing true))
             false
             "never do it"
             :else
             (nth (rest data) n))
           )
    )
  (cons (first data) (reverse @l1))
)
(defn render [{meta1 :meta posts :entries}]
  (html5 {:lang "en" :itemtype "http://schema.org/Blog"}
    [:head
      [:title (:site-title meta1)]
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0, user-scalable=no"}]]
    [:body
     [:p "This is a demonstration of a static page, for content that won't change"]
     [:br]
     [:br]
     [:br]
     [:br]
     [:h1 "BUILD-UL"]     
     [:br]
     ((comp
       page
       (partial add-feature :h1 :about)
       (partial add-feature :active :tags)
       sort-menu-data
       )
      (cons (:menu-base meta1) (:add-all-menu-data meta1)))
     [:br]
     [:br]
     [:br]
     [:br]
     [:br]
     [:br]
     ]))



;8      (= li-name :site) [:span {:style "color:steelblue; font-family:\"Times New Roman\", Times, serif"} "Trzczy"])
;9      (= li-name :site) [:span {:style "color:hotPink; font-family:\"Times New Roman\", Times, serif"} "Trzczy"])
;      (= li-name :site) [:span {:style "color:olive; font-family:\"Times New Roman\", Times, serif"} "Trzczy"])
;      (= li-name :site) [:span {:style "color:darkgreen; font-family:\"Times New Roman\", Times, serif"} "Trzczy"])
