(ns io.perun.example.menu.regen
  (require [clojure.string :as str]))

;href
(defmulti dohref #(contains? % :href))
(defmethod dohref false [item]
  (println "adding href")
  (merge item {:href (str "/" (name (:key item)) ".html")}))
(defmethod dohref true [item]
  (identity item))
;name
(defmulti doname #(contains? % :name))
(defmethod doname false [item]
  (println "adding name")
  (merge item {:name (str/capitalize (name (:key item)))}))
(defmethod doname true [item]
  (identity item))
;title
(defmulti dotitle #(contains? % :title))
(defmethod dotitle false [item]
  (println "adding title")
  (merge item {:title (str/capitalize (name (:key item)))}))
(defmethod dotitle true [item]
  (identity item))

(defn regen [list-of-maps] ;;list of maps > list of maps
  (let [at (atom ())]
    (doseq [item list-of-maps]
      (swap! at concat
          (list ((comp
                dotitle
                doname
                dohref)
              item)))) @at))
