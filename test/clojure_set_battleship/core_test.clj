(ns clojure-set-battleship.core-test
  (:require [clojure.test :refer :all]
            [clojure-set-battleship.core :refer :all]))

(deftest test-ship->points
  (testing "a ship at 0 0"
    (is (= (ship->points 3 {:x 0 :y 0} {:x 1 :y 0})
           #{{:x 0 :y 0} {:x 1 :y 0} {:x 2 :y 0}}))))
