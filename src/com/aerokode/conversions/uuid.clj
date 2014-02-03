(ns com.aerokode.conversions.uuid
  (:require [clojure.data.codec.base64 :as b64]))


(defn- uuid-to-byte-array [uuid]
  (let [msb (.getMostSignificantBits uuid)
        lsb (.getLeastSignificantBits uuid)
        bb (java.nio.ByteBuffer/allocate 16)]
    (.putLong bb msb)
    (.putLong bb lsb)
    (.array bb)))

(defn uuid-to-base64 [uuid]
  (let [ba (uuid-to-byte-array uuid)]
    (b64/encode ba)))

(defn- unpack [n b]
  (bit-or (bit-shift-left n 8) (bit-and b 0xFF)))

(defn uuid-from-base64 [ba]
  (let [msb (reduce unpack 0 (take 8 ba))
        lsb (reduce unpack 0 (take 8 (drop 8 ba)))]
    (java.util.UUID. msb lsb)))
