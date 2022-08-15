package com.envixo.coursify;

public class Utility {

    public static final String URL_CATEGORY = "https://blog.coursify.me/wp-json/wp/v2/categories/";

    public static final String URL_POST = "https://blog.coursify.me/wp-json/wp/v2/posts";

    public static final String URL_MIDIA = "https://blog.coursify.me/wp-json/wp/v2/media/";

    /*
        Para solicitar mais que 10 Categorias, usar a Formação abaixo
        https://blog.coursify.me/wp-json/wp/v2/categories/?per_page=50
    */
    public static final String URL_CATEGORY_PER_PAGE = "https://blog.coursify.me/wp-json/wp/v2/categories/?per_page=";



    public static final String URL_MIDIA_BY_ID = "https://blog.coursify.me/wp-json/wp/v2/media/?include=";

    /*
        Parâmetros para buscar post por categoria específica:
        https://blog.coursify.me/wp-json/wp/v2/posts?categories=(ID-DA-CATEGORIA) ### ID-DA-CATEGORIA: Integer
        Exmeplo : https://blog.coursify.me/wp-json/wp/v2/posts?categories=732&per_page=30
     */
    public static final String URL_POSTS_BY_ID_CATEGORY = " https://blog.coursify.me/wp-json/wp/v2/posts?categories=";
    public static final String PER_PAGE = "&per_page=";

    /* Aqui ele retorna apenas os três itens */
    //https://blog.coursify.me/wp-json/wp/v2/categories/?include=732,5,1288&per_page=50
    //https://blog.coursify.me/wp-json/wp/v2/categories/?include=732,5&per_page=50

    /* Para os Posts */
    //https://blog.coursify.me/wp-json/wp/v2/posts?categories=732&start=0&end=5
    //https://blog.coursify.me/wp-json/wp/v2/posts?categories/?start=0&end=5

    //https://blog.coursify.me/wp-json/wp/v2/posts?categories=732?include=732,5,1288&per_page=50




}
