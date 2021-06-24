<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : XMLtoJson.xsl
    Created on : 4 de Junho de 2020, 16:48
    Author     : Utilizador
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/SSFM">
        <xsl:apply-templates select="RawMaterialCategories"/>   
        <xsl:apply-templates select="RawMaterials"/>
        <xsl:apply-templates select="Consumptions"/>
    </xsl:template>
    
    <!-- Raw Material Categories -->
    <xsl:template match="RawMaterialCategories">
        "Raw Material Categories" : { "RawMaterialCategory" :[ 
        <xsl:apply-templates  select="RawMaterialCategory"/>
        ]},
    </xsl:template>
   
    <xsl:template match="RawMaterialCategory">
        {
        "name" : "<xsl:value-of select="@name"/>",
        "description" : "<xsl:value-of select="description"/>",
        },
    </xsl:template>
    
    <!-- Raw Materials -->
    <xsl:template match="RawMaterials">
        "Raw Materials" : { "RawMaterial" :[ 
        <xsl:apply-templates  select="RawMaterial"/>
        ]},
    </xsl:template>
   
    <xsl:template match="RawMaterial">
        {
        "internalCode" : "<xsl:value-of select="@internalCode"/>",
        "nameRawMaterial" : "<xsl:value-of select="@nameRawMaterialCategory"/>",
        "description" : "<xsl:value-of select="description"/>",
        "TechnicalSheet" : {
         
        "nameTechnicalSheet" : "<xsl:value-of select="TechnicalSheet/nameTechnicalSheet"/>"
        }
        },
        },
    </xsl:template>
    
    <!-- Consumptions -->
    <xsl:template match="Consumptions">
        "Consumptions" : {
        <xsl:apply-templates select="EffectiveConsumptions"/>
        <xsl:apply-templates select="RealConsumptions"/>
        },
    </xsl:template>
    
    <!-- Effective Consumptions -->
    <xsl:template match="EffectiveConsumptions">
        "EffectiveConsumptions" : { "Consumption" : [
        <xsl:apply-templates select="Consumption"/>
        ]},
    </xsl:template>
    
    <xsl:template match="//EffectiveConsumptions/Consumption">
        {
        "machine" : "<xsl:value-of select="@machine"/>",
        "deposit" : "<xsl:value-of select="@deposit"/>",
        "rawMaterial" : "<xsl:value-of select="@rawMaterial"/><xsl:value-of select="@product"/>",
        "quantity" : "<xsl:value-of select="quantity"/>",
        },
    </xsl:template>
    
    <!-- Real Consumptions -->
    <xsl:template match="RealConsumptions">
        "RealConsumptions" : {
        <xsl:apply-templates select="Deposit"/>
        <xsl:apply-templates select="RawMaterial"/>
        <xsl:apply-templates select="Product"/>
        },
    </xsl:template>
    
    <xsl:template match="//RealConsumptions/Deposit">
        "Deposit" : {
        "deposit" : "<xsl:value-of select="@id"/>",
        "quantity": "<xsl:value-of select="quantity"/>",
        },
    </xsl:template>

    <xsl:template match="//RealConsumptions/RawMaterial">
        "RawMaterial" : {
        "rawMaterial" : "<xsl:value-of select="@id"/>",
        "quantity" : "<xsl:value-of select="quantity"/>",
        },
    </xsl:template>
    
    <xsl:template match="//RealConsumptions/Product">
        "Product" : {
        "product" : "<xsl:value-of select="@id"/>",
        "quantity" : "<xsl:value-of select="quantity"/>",
        },
    </xsl:template>

</xsl:stylesheet>
