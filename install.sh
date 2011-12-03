#!/bin/sh
#echo "--- Checking system requirements..."
type -P play &>/dev/null  && continue  || { echo "ERROR: 'play' command not found."; exit 1; }
type -P git &>/dev/null  && continue  || { echo "ERROR: 'git' command not found."; exit 1; }
#echo "--- OK!"

read -p 'Enter application name (dir will be created with this name): ' app_name

echo "--- Cloning play-template from github..."
git clone git@github.com:FrostDigital/play-template.git $app_name >> /dev/null
rc=$?
if [[ $rc != 0 ]] ; then
    exit $rc
fi
echo "--- OK!"

echo "--- Removing git meta data..."
cd $app_name
rm -rf .git
rc=$?
if [[ $rc != 0 ]] ; then
    exit $rc
fi
echo "--- OK!"

echo "--- Syncing dependencies..."
play deps --sync --forProd >> /dev/null
rc=$?
if [[ $rc != 0 ]] ; then
    exit $rc
fi
echo "-- OK!"

DIR=$(pwd)
echo "Created play-template app in: ${DIR}"
echo "Start app with 'cd ${DIR}; play run'"
