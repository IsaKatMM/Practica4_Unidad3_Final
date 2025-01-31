from flask import Flask
from routes.route import router

def create_app():
    app = Flask(__name__)
    app.register_blueprint(router)
    return app
