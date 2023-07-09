import bpy

save_to_file = 'SomeFileName'

vertices = ['{"x":' + str(vert.co.x) + ',"y":' + str(vert.co.y) + ',"z":' + str(vert.co.z) + '}' for vert in bpy.context.object.data.vertices]

edges = ['{"first":' + str(edge.vertices[0]) +',"second":'+str(edge.vertices[1]) + '}' for edge in bpy.context.object.data.edges]

with open(save_to_file, 'w') as file:
    file.write('{"vertices":[' + ",".join(vertices) + '],"edges":[' + ",".join(edges) + ']}')
    file.close()