#!/usr/bin/env bash

#if [ -d classes ] ; then
#	rm -rf classes;
#fi
#
#mkdir classes

javac -cp $JAVA_HOME/lib/tools.jar  dev/jtong/demos/my/lombok/setter/MySetter* -d ./
javac -processor dev.jtong.demos.my.lombok.setter.MySetterProcessor dev/jtong/demos/my/lombok/setter/TestMySetter.java
javap -p dev/jtong/demos/my/lombok/setter/TestMySetter.class
#java -cp classes aboutjava.annotion.TestAno