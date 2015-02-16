(ns clojure-set-battleship.core)

(defn ship->coords [{length :length {x :x y :y} :start {dx :x dy :y} :orientation}]
  (set
    (for [delta (range 0 length)]
      {:x (+ x (* dx delta))
       :y (+ y (* dy delta))})))

(defn coord-sets [placements] (map ship->coords placements))

(defn collisions? [placements]
  (let [total-length (apply + (map :length placements))
        total-occupied (apply clojure.set/union  (coord-sets placements))]
    (not (= total-length total-occupied))))

(defn hit? [coords placements]
  (contains? (apply clojure.set/union (coord-sets placements)) coords))

(defn board-coords [height width]
  (set
    (for [x (range 0 width)
          y (range 0 height)]
      {:x x :y y})))

(defn on-board? [{height :height width :width} placement]
  (every? #(contains? (board-coords height width) %) (ship->coords placement)))

(defn count-remaining [hits placements]
  (let [ship-coordinate-sets-without-hits
        (reduce
          (fn [sets hit] (map #(clojure.set/difference % #{hit}) sets))
          (coord-sets placements)
          hits)]
    (->> ship-coordinate-sets-without-hits (filter (complement empty?)) count)))

(defn sunk? [hit previous-hits placements]
  (let [total-ships-before-hit (count-remaining previous-hits placements)
        total-ships-after-hit  (count-remaining (cons hit previous-hits) placements)]
    (not (= total-ships-before-hit total-ships-after-hit))))

(defn win? [hit previous-hits placements]
  (zero? (count-remaining (cons hit previous-hits) placements)))
