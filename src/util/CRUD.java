/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Map;

/**
 *
 * @author evson
 */
public interface CRUD<K, V> {
    public void create(Model model);
    public V get(Model model);
    public Map<K, V> query();
    public void update(Model model);
    public void delete(Model model);
}
