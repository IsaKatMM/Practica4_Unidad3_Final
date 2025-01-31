from flask import Blueprint, abort, request, render_template, redirect, flash, jsonify 
  

import requests
import json


router= Blueprint('router',__name__)

@router.route('/')
def home():
    return render_template('templateFirst.html')


@router.route('/list')
def list_graph():
    try:
        r = requests.get("http://localhost:8099/myapp/iglesias/list")
        data = r.json()
        return render_template('iglesias/lista.html', lista=data["data"])
    except Exception as e:
        print(f"Error al obtener datos: {str(e)}")
        return render_template('iglesias/lista.html', lista=[])


###DFS busqueda en profundidad

@router.route('/dfs/<int:origen>', methods=['GET'])
def dfs(origen):
    try:
        url = f"http://localhost:8099/myapp/iglesias/dfs/{origen}"
        r = requests.get(url)
        if r.status_code == 200:
            if r.text.strip():
                data = r.json()
                return jsonify(data)
            else:
                return jsonify({"error": "La respuesta está vacía"}), 500
        else:
            return jsonify({"error": f"Error al ejecutar DFS. Código de estado: {r.status_code}"}), r.status_code

    except requests.exceptions.RequestException as e:
        print("Error de conexión:", str(e))
        return jsonify({"error": "No se pudo conectar con el servidor de DFS"}), 500



@router.route('/mapa')
def map():
    r = requests.get("http://localhost:8099/myapp/iglesias/mapa")
    
    try:
       if r.status_code == 200:
            graph_data = r.json()
            return render_template('iglesias/grafo.html', graph_data=graph_data)
       else:
            print("Error al obtener grafo: ", r.text)
            return jsonify({"error": "No se pudo obtener el grafo"}), r.status_code

    except requests.exceptions.RequestException as e:
        print("Error de conexión: ", str(e))
        return jsonify({"error": "No se pudo conectar con el servidor"}), 500

@router.route('/ady')
def ady():
    r = requests.get("http://localhost:8099/myapp/iglesias/grafos")
    print(r.status_code) 
    print(r.text)         

    data = r.json()
   
    print(data)
    return render_template('iglesias/ady.html', lista=data)


@router.route('/calculo_camino_corto/<int:origen>/<int:destino>/<int:algoritmo>', methods=['GET'])
def calc(origen, destino, algoritmo):
    try:
        url = f"http://localhost:8099/myapp/iglesias/caminoCorto/{origen}/{destino}/{algoritmo}"
        r = requests.get(url)

        if r.status_code == 200:
            data = r.json()
            return jsonify(data)
        else:
            return jsonify({"error": "error"}), r.status_code

    except requests.exceptions.RequestException as e:
        print("Error de conexión:", str(e))
        return jsonify({"error": "error"}), 500
    
@router.route('/path', methods=['GET'])
def frm():
    r = requests.get("http://localhost:8099/myapp/iglesias/grafos")
    data = r.json()
    return render_template('iglesias/path.html', data= data) 
