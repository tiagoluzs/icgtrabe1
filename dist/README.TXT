java -jar IcgTrabE1.jar <cmd> <colors> source target
cmd: color|gray
colors: >0
source: <file>
target: <file>

java -jar IcgTrabE1.jar gray 2 ../ppm/Eagle.ppm ../ppm/Eagle-2.ppm
java -jar IcgTrabE1.jar gray 16 ../ppm/Eagle.ppm ../ppm/Eagle-16.ppm
java -jar IcgTrabE1.jar color 100 ../ppm/Eagle.ppm ../ppm/Eagle-100.ppm