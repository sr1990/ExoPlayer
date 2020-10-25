POC for SBD support
Partial implementation for supporting sbd essential property.
Currently only works with Orderline table (Examples in the server files.)
Does not support sbd template for adding queries.

Examples taken from:https://github.com/MPEGGroup/DASHPart8

Setup:
Servers
1. DASH content: Created with shaka packager using PR which adds EP at adaptation set level: 
https://github.com/google/shaka-packager/pull/856
Example: 

../packager \                                                                   
 'in=Sintel-1024x576.mp4,stream=video,init_segment=h264_1024p/init.mp4,segment_template=h264_1024p/$Number$.m4s' \
  'in=Sintel-1280x720.mp4,stream=video,init_segment=h264_1280p/init.mp4,segment_template=h264_1280p/$Number$.m4s' \
  --mpd_output h264_sbd.mpd --generate_static_live_mpd --sbd_url_all "http://10.0.0.220:3001/,http://10.0.0.220:3002/" --sbd_template_all "t1,t2" --sbd_key_all "key1=v1,key2=v2:key3=v3,key4=v4" 

Dash Content in sbdContent folder.

Content server folder:
goServer/testServer.go -> point this to sbdContent folder.
go mod init testServer
go get
go run testServer.go 


2. 2 for SBD files server: index.js index2.js
   Folder: sbdFileServer
npm init 
npm install express
node index.js
node index2.js 

3. In Exoplayer demo.
