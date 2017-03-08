#this is the master server that handles both HTTP posts and gets
import BaseHTTPServer
from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
import requestHandler as rHandler

address = "192.168.2.15" #TODO find local IP
PORT = 9214

class RequestHandler(BaseHTTPRequestHandler):
    def setup(self):
        BaseHTTPServer.BaseHTTPRequestHandler.setup(self)
        self.request.settimeout(20)
        self.rfile._sock.settimeout(20)
    def do_POST(self): #handle requests here
        print(self.path)
        bodyLength = int(self.headers.getheader('Content-Length'))
        body = self.rfile.read(bodyLength)
        body = body.split('\n')
        print('body of post read, passing to handler')
        response = rHandler.handlePost(self.path, body)
        self.send_response(200, "OK")
        self.end_headers()
        self.wfile.write(response)
        return
    def do_GET(self):
        print(self.path)
        body = rHandler.handleGet(self.path)
        #print("Sending Response: " + body) for testing
        self.send_response(200, "OK")
        self.send_header("Content-type", "text/plain")
        self.send_header("Content-length", str(len(body)))
        self.end_headers()
        self.wfile.write(body)
        print("response sent")
        return

httpd = HTTPServer((address, PORT), RequestHandler)
print("online at port " + str(PORT))
httpd.serve_forever()
