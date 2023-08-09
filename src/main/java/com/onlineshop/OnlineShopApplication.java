package com.onlineshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Администрирование справочников (admin):
 * + редактирование справочника категорий (categories)
 * + редактирование справочника стран (countries)
 * + редактирование справочника поставшиков (suppliers)
 * + редактирование справочника покупателей (customers)
 * + редактирование справочника товаров (products)
 * + редактирование справочника магазинов (shops)
 *
 * Управление товарными запасами (admin):
 * - оформление поставки товара в магазин(ы)
 * - оформление отгрузки товара покупателю согласно заказу
 *
 * Операции с заказами (user):
 * + создать заказ с товаром
 * + добавить товар в заказ
 * + удалить товар из заказа
 * + отправить заказ на исполнение
 *
 * Операции с товарными позициями (доступно для всех):
 * - найти все товарные позиции
 * - найти товарную позицию по id
 * - найти товарные позиции по части описания
 * - найти товарные позиции по категории
 *
 * Модуль работы с пользователями:
 * - регистрация пользователя
 */

@SpringBootApplication
public class OnlineShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopApplication.class, args);
    }

}
