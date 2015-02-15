(ns clojure-set-battleship.core)

(defn ship->points [size {x :x y :y} {dx :x dy :y :as thing}]
  (set
    (for [delta (range 0 size)]
      {:x (+ x (* dx delta))
        :y (+ y (* dy delta))})))

(defn id+coords=>coords->id [id coords]
  (reduce #(assoc %1 %2 id) {} coords))

(defn ship-placements=>points->ship-id [placements]
  (let [id-coords-pairs
        (map-indexed (fn [ship-id placement] [ship-id (apply ship->points placement)]) placements)]
    (apply merge (map #(apply id+coords=>coords->id %) id-coords-pairs))))


(defn collisions? [placements]
  (let [point-sets (map #(apply ship->points %) placements)
        total-points-in-sets (apply + (map count point-sets))
        total-of-union (apply clojure.set/union point-sets)]
    (not (= total-points-in-sets total-of-union))))
