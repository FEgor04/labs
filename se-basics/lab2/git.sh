#/bin/bash

export MAIN_BRANCH=main

branch() {
  git branch $1
}

checkout() {
  git checkout $1
  git status
}

init() {
  git init $1
}

commit() {
  git commit -m $1
}

add_all() {
  git add .
}

set_first_user() {
  git config user.name "Egor Fedorov"
  git config user.email "fegor2004@gmail.com"
}

set_second_user() {
  git config user.name "Andrey Karabanov"
  git config user.email "deskpa@yandex.ru"
}

merge() {
  git merge $1
}

unzip_commit(){
  unzip -o -d . ../commits/$1.zip
}

commit_revision() {
  unzip_commit $1
  add_all
  commit $1
}

rm -rf git
init git
cd git

set_first_user
checkout $MAIN_BRANCH
commit_revision r0


set_second_user
branch blue_branch
checkout blue_branch
commit_revision r1

set_first_user
branch red_branch
checkout red_branch
commit_revision r2

checkout $MAIN_BRANCH
commit_revision r3

set_second_user
checkout blue_branch
commit_revision r4

set_first_user
checkout red_branch
commit_revision r5

set_second_user
checkout blue_branch
commit_revision r6
commit_revision r7

set_first_user
checkout red_branch
commit_revision r8

checkout $MAIN_BRANCH
commit_revision r9

set_second_user
checkout blue_branch
commit_revision r10

set_first_user
checkout red_branch
merge blue_branch
commit_revision r11

commit_revision r12
commit_revision r13

checkout $MAIN_BRANCH
merge red_branch
commit_revision r14

