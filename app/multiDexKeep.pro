# 将需要合到主dex包的类添加到multiDexKeep.pro 和 multiDexKeep.txt中 防止NoClassDefFound异常
#   例: multiDexKeep.pro  -keep class com.vocinno.centanet.tools.LocationUtil$MyLocationListenner { *; }
#       multiDexKeep.txt  com/vocinno/utils/LocationUtil$MyLocationListenner.class