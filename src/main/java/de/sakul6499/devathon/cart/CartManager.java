package de.sakul6499.devathon.cart;

import de.sakul6499.devathon.util.JSONLocation;
import org.bukkit.Location;
import org.json.simple.JSONArray;

import java.util.LinkedList;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by lukas on 05.11.16.
 */
public final class CartManager {

    /* Static */

    private static final CartManager INSTANCE;

    static {
        INSTANCE = new CartManager();
    }

    public static CartManager GetInstance() {
        return INSTANCE;
    }

    /* Class */

    private final LinkedList<CartModel> cartList;

    private CartManager() {
        cartList = new LinkedList<>();
    }

    public boolean registerCart(CartModel cart) {
        if (cart == null) {
            System.out.println("Cart will not be added to Manager! [NULL]");
            return false;
        }

        if (cartList.stream().anyMatch(Cart -> Cart.equals(cart) && Cart.getName().equals(cart.getName()))) {
            return false;
        }

        cartList.push(cart);
        return true;
    }

    public void spawnAll() {
        for (CartModel cart : cartList) {
            cart.update();
        }
    }

    public void killAll() {
        for (CartModel cart : cartList) {
            cart.kill();
        }
    }

    public boolean kill(UUID id) {
        CartModel cartModel = getByID(id);
        if(cartModel == null) return false;

        remove(cartModel);
        cartModel.dropItem();
        return true;
    }

    public boolean kill(Location location) {
        CartModel cartModel = getByLocation(location);
        if (cartModel == null) return false;

        remove(cartModel);
        cartModel.dropItem();
        return true;
    }

    public boolean remove(UUID id) {
        CartModel cartModel = getByID(id);
        if (cartModel == null) return false;

        remove(cartModel);
        return true;
    }

    public boolean remove(Location location) {
        CartModel cartModel = getByLocation(location);
        if (cartModel == null) return false;

        remove(cartModel);
        return true;
    }

    public boolean remove(String name) {
        CartModel cartModel = getByName(name);
        if(cartModel == null) return false;

        remove(cartModel);
        return true;
    }

    public void remove(CartModel cartModel) {
        cartModel.kill();
        cartList.remove(cartModel);
    }

    public CartModel getByID(UUID id) {
        return cartList.stream().filter(Cart -> Cart.getCartID().equals(id)).findFirst().orElse(null);
    }

    public CartModel getByLocation(JSONLocation location) {
        return cartList.stream().filter(Cart -> Cart.getJSONLocation().coordinatesNormalEqual(location)).findFirst().orElse(null);
    }

    public CartModel getByName(String name) {
        return cartList.stream().filter(Cart -> Cart.getName().equals(name)).findFirst().orElse(null);
    }

    public CartModel getByLocation(Location location) {
        return getByLocation(new JSONLocation(location));
    }

    @Override
    public String toString() {
        return cartList.stream().map(CartModel::toString).collect(Collectors.toCollection(JSONArray::new)).toJSONString();
    }
}
