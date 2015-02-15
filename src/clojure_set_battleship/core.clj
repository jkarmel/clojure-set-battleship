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


(defn point-sets [placements]
  (map #(apply ship->points %) placements))

(defn collisions? [placements]
  (let [total-points-in-sets (apply + (map count (point-sets placements)))
        total-of-union (apply clojure.set/union  (point-sets placements))]
    (not (= total-points-in-sets total-of-union))))

(defn all-coords-with-ships [placements]
  (apply clojure.set/union (map #(apply ship->points %) placements)))

(defn hit? [coords placements]
  (not (contains? placements coords)))
