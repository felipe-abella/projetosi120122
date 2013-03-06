#!/bin/bash
cd ../testesDeAceitacao_SI1
echo Updating GIT repository...
git pull || exit 1
echo Copying User Stories
cp scripts/US*.txt ../projetosi120122/easyaccept_tests/ || exit 1
cd ../projetosi120122/
echo Success

