import random

class Order:
    def __init__(self, customer_name: str, customer_address: str, ingredients):
        self.status = "ordered"
        self.customer = {
          "name": customer_name,
          "address": customer_address,
        }
        self.ingredints = ingredients

    def set_delivered(self):
        self.status = "delivered"

    def set_making(self):
        self.status = "making"

    def __str__(self):
        return f'{self.id} {self.status} {self.customer} {self.ingredints}'

class CopyPizza:
    def __init__(self):
        self._orders = []
        with open("tilaukset.csv", "r") as file:
            for row in file:
                parts = row.strip().split(";")
                order = Order(parts[2], parts[3], parts[4].split(','))
                order.id = int(parts[0])
                order.status = parts[1]
                self._orders.append(order)

    def save(self):
        with open("tilaukset.csv", "w") as file:
            for order in self._orders:
                ingredients = ','.join(order.ingredints)
                file.write(f'{order.id};{order.status};{order.customer["name"]};{order.customer["address"]};{ingredients}\n')

    def list_delivered(self):
        matches = []
        for order in self._orders:
            if order.status == "delivered":
                matches.append(order)

        return matches

    def list_new(self):
        matches = []
        for order in self._orders:
            if order.status == "ordered":
                matches.append(order)

        return matches

    def list_customer(self, customer_name: str):
        matches = []
        for order in self._orders:
            if order.customer.name == customer_name:
                matches.append(order)

        return matches

    def list_ingredient(self, ingredient: str):
        matches = []
        for order in self._orders:
            if ingredient in order.ingredients:
                matches.append(order.set_delivered())

        return matches

    def mark_delivered(self, id: int):
        for order in self._orders:
            if order.id == id:
                order.set_delivered()

    def mark_making(self, id: int):
        for order in self._orders:
            if order.id == id:
                order.set_making()

    def take_order(self, order: Order):
        order.id = random.randint(1,10000000)
        self._orders.append(order)

    def export(self, format: str, status: str):
        if format=='xml':
            print('<orders>')

        for order in self._orders:
            if format=='xml':
                if order.status == status:
                    print("  <order>")
                    print(f"    <id>{order.id}</id>")
                    print(f"    <customer><name>{order.customer['name']}</name><address>{order.customer['address']}</address></customer>")
                    print(f"    <numberOfIngredients>{len(order.ingredints)}</numberOfIngredients>")
                    print("  </order>")
            elif format=='tsv':
                if order.status == status:
                    print(f"{order.id}\t{order.customer['name']}\t{order.customer['address']}\t{len(order.ingredints)}")
            else:
              raise('unsupported format')

        if format=='xml':
          print('</orders>')

def main():
  pizzeria = CopyPizza()

  order = Order("Kalle Ilves", "Koskelantie 100", ["kinkku", "jauheliha", "pekoni", "kebab", "salami"])
  pizzeria.take_order(order)

  pizzeria.mark_making(order.id)
  pizzeria.mark_delivered(order.id)

  order = Order("Riikka Korolainen", "Kumpulankaari 55", ["kinkku", "ananas", "aurajuusto"])
  pizzeria.take_order(order)

  for pizza in pizzeria.list_delivered():
      print(pizza)

  pizzeria.export("xml","delivered")
  pizzeria.save()

main()
