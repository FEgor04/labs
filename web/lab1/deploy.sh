pnpm run build
sed -i 's/\/assets/\.\/assets/g' dist/index.html
sed -i "s/GIT_VERSION/$(git rev-parse --short HEAD)/g" dist/index.html
echo 'Uploading dist and php to helios'
rsync -r {dist,php}/* helios:~/public_html
echo 'FINISHED'
