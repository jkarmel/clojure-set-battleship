(ns clojure-set-battleship.core-test
  (:require [clojure.test :refer :all]
            [clojure-set-battleship.core :refer :all]))

(let [origin {:x 0 :y 0}
      off-board {:x -1 :y 0}
      down   {:x 0 :y 1}
      right  {:x 1 :y 0}
      random-coords {:x 5 :y 5}
      down-one down
      small-ship-at-origin [2 origin down]
      large-ship-at-origin [4 origin down]]

  (deftest -ship->coords
    (is (= (apply ship->coords small-ship-at-origin)
          #{origin down-one})))

  (deftest -on-board?
    (is (not (on-board? {:height 10 :width 10} [2 off-board down]))))

  (deftest -collisions?
    (testing "placing overlapping ships"
      (is (collisions? [small-ship-at-origin large-ship-at-origin]))))

  (deftest -hit?
    (is (hit? origin [small-ship-at-origin]))
    (is (hit? random-coords [small-ship-at-origin])))

  (deftest -count-remaining
    (is (= (count-remaining [] [small-ship-at-origin]) 1))
    (is (= (count-remaining [origin down-one] [small-ship-at-origin]) 0)))

  (deftest -sunk?
    (is (sunk? origin [down-one] [small-ship-at-origin])))

  (deftest -win?
    (is (win? origin [down-one] [small-ship-at-origin]))
    (is (not (win? origin [random-coords] [small-ship-at-origin])))))
