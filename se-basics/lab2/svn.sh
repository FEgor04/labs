#/bin/bash

branch() {
  svn copy $REPO/trunk $REPO/branches/$1 -m "Creating branch $1" --username $USER
}

checkout() {
  svn co $REPO/branches/$1 --username $USER
  cd $1
}

init_env_variable(){
current_dir=$(pwd)
export REPO=file:///$current_dir
}

commit() {
  echo "Commiting $1"
  add_all
  svn commit -m $1 --username $USER
}

add_all() {
  svn add * --force
}

update(){
  svn update
}

init(){
svn mkdir -m "make branches dir" $REPO/branches --username $USER
svn mkdir -m "trunk" $REPO/trunk --username $USER
}

set_first_user() {
  export USER="Egor"
  svn update
}

set_second_user() {
  export USER="Andrey"
  svn update
}

merge() {
  update
  svn merge $REPO/branches/$1 --username $USER
  update
}

unzip_commit(){
  unzip -o -d . ../../commits/$1.zip
}

commit_revision_add() {
  unzip_commit $1
  add_all
  commit $1
  update
}
commit_revision_update() {
  unzip_commit $1
  update
  commit $1
  update
  svn log
}
rm -rf ./lab
rm -rf ./workdir

rm -rf ./blue_branch
rm -rf ./red_branch

set_first_user
svnadmin create lab
cd lab
init_env_variable
init

set_first_user
branch red_branch
checkout red_branch
commit_revision_add r0

cd ..

set_second_user
svn copy $REPO/branches/red_branch $REPO/branches/blue_branch -m "Creating blue_branch based on red_branch" --username $USER
checkout blue_branch
commit_revision_update r1


cd ..

set_first_user
svn copy $REPO/branches/blue_branch $REPO/branches/red_branch_2 -m "Creating red_branch_2 based on blue_branch" --username $USER
checkout red_branch_2
commit_revision_update r2

cd ..

set_first_user
checkout red_branch
commit_revision_update r3

cd ..

set_second_user
checkout blue_branch
commit_revision_update r4

cd ..

set_first_user
checkout red_branch_2
commit_revision_update r5
cd ..

set_second_user
checkout blue_branch
commit_revision_update r6
commit_revision_update r7
cd ..

set_first_user
checkout red_branch_2
commit_revision_update r8
cd ..
set_first_user
checkout red_branch
commit_revision_update r9

cd ..
set_second_user
checkout blue_branch
commit_revision_update r10

cd ..

set_first_user
checkout red_branch_2
merge blue_branch
commit_revision_update r11
commit_revision_update r12
commit_revision_update r13

cd ..
set_first_user
checkout red_branch
merge red_branch_2
commit_revision_update r14

