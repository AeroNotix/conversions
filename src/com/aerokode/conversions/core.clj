(ns com.aerokode.conversions.core)

(def ^:private type-max
  {'byte Byte/MAX_VALUE,
   'short Short/MAX_VALUE,
   'int Integer/MAX_VALUE})
 
(defn ^:private signed*
  [type x]
  (let [max (type-max type), roll (->> max long inc (* 2))]
    `(~type (let [x# (long ~x)] (if (<= x# ~max) x# (- x# ~roll))))))
 
(defn ^:private unsigned*
  [type x]
  (let [max (type-max type), roll (->> max long inc (* 2))]
    `(let [x# (long ~x)] (if (<= 0 x#) x# (+ x# ~roll)))))
 
(defn ^:private signvert-doc
  [type f]
  (if (= f 'signed*)
    (str "Convert unsigned integral value to signed " type ".")
    (str "Convert signed " type " to unsigned integral value.")))
 
(defmacro ^:private define-signverters
  [& args]
  `(do ~@(mapcat (fn [[type names]]
                   (map (fn [n f]
                          `(definline ~n
                             ~(signvert-doc type f)
                             [~'x] (~f '~type ~'x)))
                        names '[signed* unsigned*]))
                 (partition 2 args))))
 
(define-signverters
  byte  [sbyte  ubyte]
  short [sshort ushort]
  int   [sint   uint])
