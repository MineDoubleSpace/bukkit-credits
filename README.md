#Usage

Please note that other configuration like: Prefix, Start amount are configured on the database. 

Database Config:
   Example:
       Config               Value
       "prefix"           &4[prefix]
       "start amount"         20  

   Prefix : Message prefix when sending a message to someone
   start amount: Amount of credits player will receive when first join
           Note  
       This is not first time join to a server, This is if player does not exits on Database
 


Commands:
   /Credits set <player> <amount>
       permissions: credits.admin
   /Credits add <player> <amount>
       permissions: credits.admin
   /Credits remove <player> <amount>
       permissions: credits.admin
   /Credits multiplier <player> <amount>
       permissions: credits.admin
          
   /Shop
       permissions: credits.shop
           permissions is only enabled if "use-permissions" is set to true,



Permissions:
   creditshop.donoracsess
       give access to donator only items.



multipliers:
   credits.multiplier.2
   credits.multiplier.1.75
   credits.multiplier.1.5
   credits.multiplier.1.25
       Default: 1



Shop:
   ID: 
      Leave it empty, ID is the Unique key to DB, not used for Shop

   item: 
       Item ID (Do not include item Data)
           Example: if item ID is 35:1 for Orange Wool, "item" is 35, Do not include ":1"

   name: 
       Name of the Item, (Chat Colour codes are supported)

 	enchantments:
       Enchantment ID are listed here : http://www.minecraftforum.net/topic/1711629-enchantment-id-list/
       Example: For Protection ID is 0, And if you would like "Protection 3" On the Item,
                It would be 0:3 
       Multiple Enchantments: Separate Enchantment with a "," (Example: 0:3,31:2,35:1)
 
   donator:
       Options: 
           true: Donators permissions needed (creditshop.donoracsess)
           false: No permissions needed 

   cost: 
       Cost of the Item 

   byte:
       Example: Example: if item is Orange Wool, Then item ID is 35:1
                byte Would be 1 (Do not include :, Just 1)
        Leave it "0" for no Item Data
		
		
#Database config#


Database name
database-name: "db"


Database Host(IP) use "Localhost" if database is hosted locally
database-host: "localhost"


Database username, make sure user have permissions to Create/Update/Insert/Read
database-user: "username"


Database password, password for username above
database-pass: "password"


Database port, Default port for mysql: "3306"
database-port: "3306"


Permissions node for shop: credits.shop
if set to false, will not check if player have permissions to use shop
use-permissions: false



