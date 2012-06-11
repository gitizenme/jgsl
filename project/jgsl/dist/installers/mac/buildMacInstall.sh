#!/bin/sh

cp $BUILDROOT/lib/spitzer/*.jar ispot.app/Contents/Resources/Java/
cp $COMMON_MODULE/dist/jars/*.jar ispot.app/Contents/Resources/Java/
cp $ISPOT_MODULE/dist/jars/*.jar ispot.app/Contents/Resources/Java/

tar cvf ispot-mac.tar ispot.app