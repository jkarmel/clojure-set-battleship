(ns clojure-set-battleship.core)

(defn ship->points [size {x :x y :y} {dx :x dy :y :as thing}]
  (set
    (for [delta (range 0 size)]
      {:x (+ x (* dx delta))
        :y (+ y (* dy delta))})))
