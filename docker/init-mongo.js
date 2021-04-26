let error = true

let res = [
    db = new Mongo().getDB("messenger");

    db.createCollection('MessengerUser');

    // TODO Also create indexes, etc...
]

printjson(res)

if (error) {
  print('Error, exiting')
  quit(1)
}
