import os, shutil
s = "app/src/commonMain/composeResources/files"
d = "app/src/androidMain/res/raw"
files = os.listdir(s)
for filename in files:
    shutil.copy2(os.path.join(s, filename), d)