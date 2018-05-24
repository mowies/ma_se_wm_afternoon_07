#!/bin/bash

./gradlew :backend-treasurehunt:build
cd backend-treasurehunt/build/distributions

zip_file=backend-treasurehunt-boot-*.zip
folder=`basename ${zip_file} .zip`
rm -rf ${folder}
unzip ${zip_file}

echo "Folder is $folder"
cd ${folder}/bin
./backend-treasurehunt