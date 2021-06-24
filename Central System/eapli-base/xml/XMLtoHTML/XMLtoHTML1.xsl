<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xhtml="http://www.w3.org/1999/xhtml" version="2.0">
<xsl:output method="xhtml" omit-xml-declaration="yes" indent="yes" />

    <xsl:template match="/SSFM">
        <html>
            <head>
                <title>Factory floor</title>
            </head>
            <body>
                <h1>Factory Floor</h1>
                <xsl:apply-templates select="RawMaterialCategories" />
                <xsl:apply-templates select="RawMaterials" />
                <xsl:apply-templates select="Products" />
                <xsl:apply-templates select="Deposits" />
                <xsl:apply-templates select="ProductionSheets" />
                <xsl:apply-templates select="Lots" />
                <xsl:apply-templates select="ProductionOrders" />
                <xsl:apply-templates select="ProductionLines" />
                <xsl:apply-templates select="Consumptions" />
                <xsl:apply-templates select="Machines" />
                <xsl:apply-templates select="Wastes" />
                <xsl:apply-templates select="EffectiveTimes" />
                <xsl:apply-templates select="BruteTimes" />
            </body>
        </html>
    </xsl:template>

    <!-- Raw Materials Categories-->
    <xsl:template match="RawMaterialCategories">
        <table border="1">
            <h2>Raw Material Categories</h2>
            <tr>
                <th>Name</th>
                <th>Description</th>
            </tr>
            <xsl:apply-templates select="RawMaterialCategory">
                <xsl:sort select="@name" data-type="text" order="ascending" />
            </xsl:apply-templates>
        </table>
    </xsl:template>

    <xsl:template match="RawMaterialCategory">
        <tr>
            <td>
                <xsl:value-of select="@name" />
            </td>
            <td>
                <xsl:value-of select="description" />
            </td>
        </tr>
    </xsl:template>

    <!-- Raw Materials -->
    <xsl:template match="RawMaterials">
        <table border="1">
            <h2>Raw Materials</h2>
            <tr>
                <th>Internal Code</th>
                <th>Category</th>
                <th>Description</th>
                <th>Technical Sheet</th>
            </tr>
            <xsl:apply-templates select="RawMaterial">
                <xsl:sort select="@interalCode" data-type="text" order="ascending" />
            </xsl:apply-templates>
        </table>
    </xsl:template>

    <xsl:template match="RawMaterial">
        <tr>
            <td>
                <xsl:value-of select="@internalCode" />
            </td>
            <td>
                <xsl:value-of select="@nameRawMaterialCategory" />
            </td>
            <td>
                <xsl:value-of select="description" />
            </td>
            <td>
                <xsl:value-of select="TechnicalSheet/nameTechnicalSheet" />
            </td>
        </tr>
    </xsl:template>

    <!-- Products -->
    <xsl:template match="Products">
        <table border="1">
            <h2>Products</h2>
            <tr>
                <th>Fabrication Code</th>
                <th>Comercial Code</th>
                <th>Brief Description</th>
                <th>Complete Description</th>
                <th>Product Category</th>
                <th>Unity</th>
                <th>Production Sheet</th>
            </tr>
            <xsl:apply-templates select="Product" />
        </table>
    </xsl:template>

    <xsl:template match="Product">
        <tr>
            <td>
                <xsl:value-of select="@fabricationCode" />
            </td>
            <td>
                <xsl:value-of select="comercialCode" />
            </td>
            <td>
                <xsl:value-of select="briefDescription" />
            </td>
            <td>
                <xsl:value-of select="completeDescription" />
            </td>
            <td>
                <xsl:value-of select="productCategory" />
            </td>
            <td>
                <xsl:value-of select="unity" />
            </td>
            <td>
                <xsl:value-of select="productionSheet" />
            </td>
        </tr>
    </xsl:template>

    <!-- Deposits -->
    <xsl:template match="Deposits">
        <table border="1">
            <h2>Deposits</h2>
            <tr>
                <th>Internal Code</th>
                <th>Description</th>
                <th>Raw Materials</th>
                <th>Quantities</th>
                <th>Products</th>
                <th>Quantities</th>
            </tr>
            <xsl:apply-templates select="Deposit" />
        </table>
    </xsl:template>

    <xsl:template match="Deposit">
        <tr>
            <td>
                <xsl:value-of select="@internalCode" />
            </td>
            <td>
                <xsl:value-of select="Description" />
            </td>
            <td>
                <xsl:for-each select="RawMaterials/RawMaterial">
                    <br>
                        <xsl:value-of select="@ID" />
                    </br>
                </xsl:for-each>
            </td>
            <td>
                <xsl:for-each select="RawMaterials/RawMaterial">
                    <br>
                        <xsl:value-of select="@Quantity" />
                    </br>
                </xsl:for-each>
            </td>
            <td>
                <xsl:for-each select="Products/Product">
                    <br>
                        <xsl:value-of select="@ID" />
                    </br>
                </xsl:for-each>
            </td>
            <td>
                <xsl:for-each select="Products/Product">
                    <br>
                        <xsl:value-of select="@Quantity" />
                    </br>
                </xsl:for-each>
            </td>
        </tr>
    </xsl:template>

    <!-- Production Sheets> -->
    <xsl:template match="ProductionSheets">
        <table border="1">
            <h2>Production Sheets</h2>
            <tr>
                <th>Production Sheet ID</th>
                <th>Products</th>
                <th>Quantity</th>
                <th>Raw Materials</th>
                <th>Quantity</th>
            </tr>
            <xsl:apply-templates select="ProductionSheet" />
        </table>
    </xsl:template>

    <xsl:template match="ProductionSheet">
        <tr>
            <th>
                <xsl:value-of select="@ID" />
            </th>
            <xsl:apply-templates select="ProductionSheetLineProduct" />
            <xsl:apply-templates select="ProductionSheetLineRawMaterial" />
        </tr>
    </xsl:template>

    <xsl:template match="ProductionSheetLineProduct">
        <td>
            <xsl:for-each select="Product">
                <br>
                    <xsl:value-of select="@ID" />
                </br>
            </xsl:for-each>
        </td>
        <td>
            <xsl:for-each select="Product">
                <br>
                    <xsl:value-of select="@Quantity" />
                </br>
            </xsl:for-each>
        </td>
    </xsl:template>

    <xsl:template match="ProductionSheetLineRawMaterial">
        <td>
            <xsl:for-each select="RawMaterial">
                <br>
                    <xsl:value-of select="@ID" />
                </br>
            </xsl:for-each>
        </td>
        <td>
            <xsl:for-each select="RawMaterial">
                <br>
                    <xsl:value-of select="@Quantity" />
                </br>
            </xsl:for-each>
        </td>
    </xsl:template>

    <!-- Lots -->
    <xsl:template match="Lots">
        <table border="1">
            <h2>Lots</h2>
            <tr>
                <th>Internal Code</th>
            </tr>
            <xsl:apply-templates select="Lot" />
        </table>
    </xsl:template>

    <xsl:template match="Lot">
        <tr>
            <td>
                <xsl:value-of select="@internalCode" />
            </td>
        </tr>
    </xsl:template>

    <!-- Production Orders -->
    <xsl:template match="ProductionOrders">
        <table border="1">
            <h2>Production Orders</h2>
            <tr>
                <th>Internal Code</th>
                <th>Description</th>
                <th>State</th>
                <th>Lot</th>
                <th>Request</th>
                <th>Emission Date</th>
                <th>Predicted Execution Date</th>
                <th>Production Sheet ID</th>
                <th>Quantity To Produce</th>
            </tr>
            <xsl:apply-templates select="ProductionOrder" />
        </table>
    </xsl:template>

    <xsl:template match="ProductionOrder">
        <tr>
            <td>
                <xsl:value-of select="@internalCode" />
            </td>
            <td>
                <xsl:value-of select="Description" />
            </td>
            <td>
                <xsl:value-of select="State" />
            </td>
            <td>
                <xsl:value-of select="Lot/@ID" />
            </td>
            <td>
                <xsl:value-of select="Request/@ID" />
            </td>
            <td>
                <xsl:value-of select="EmissionDate" />
            </td>
            <td>
                <xsl:value-of select="PredictedExecutionDate" />
            </td>
            <td>
                <xsl:value-of select="ProductionSheet/@ID" />
            </td>
            <td>
                <xsl:value-of select="QuantityToProduce" />
            </td>
        </tr>
    </xsl:template>

    <!-- Production Lines -->
    <xsl:template match="ProductionLines">
        <table border="1">
            <h2>Production Lines</h2>
            <tr>
                <th>Internal Code</th>
                <th>Description</th>
                <th>Machines</th>
            </tr>
            <xsl:apply-templates select="ProductionLine" />
        </table>
    </xsl:template>

    <xsl:template match="ProductionLine">
        <tr>
            <td>
                <xsl:value-of select="@internalCode" />
            </td>
            <td>
                <xsl:value-of select="Description" />
            </td>
            <td>
                <xsl:for-each select="Machines/Machine">
                    <br>
                        <xsl:value-of select="@ID" />
                    </br>
                </xsl:for-each>
            </td>
        </tr>
    </xsl:template>

    <!-- Consumptions-->
    <xsl:template match="Consumptions">
        <h2>Consumptions</h2>
        <xsl:apply-templates select="EffectiveConsumptions" />
        <xsl:apply-templates select="RealConsumptions" />
    </xsl:template>

    <!-- Effective Consumptions -->
    <xsl:template match="EffectiveConsumptions">
        <table border="1">
            <h2>Effective Consumptions</h2>
            <tr>
                <th>Machine</th>
                <th>Deposit</th>
                <th>Raw Material</th>
                <th>Quantity</th>
            </tr>
            <xsl:apply-templates select="Consumption" />
        </table>
    </xsl:template>

    <xsl:template match="Consumption">
        <tr>
            <td>
                <xsl:value-of select="@machine" />
            </td>
            <td>
                <xsl:value-of select="@deposit" />
            </td>
            <td>
                <xsl:value-of select="@rawMaterial" />
                <xsl:value-of select="@product" />
            </td>
            <td>
                <xsl:value-of select="quantity" />
            </td>
        </tr>
    </xsl:template>

    <!-- RealConsumptions -->
    <xsl:template match="RealConsumptions">
        <h2>Real Consumptions</h2>
        <xsl:call-template name="DepositConsumption" />
        <xsl:call-template name="RawMaterialConsumption" />
        <xsl:call-template name="ProductConsumption" />
    </xsl:template>

    <xsl:template name="RawMaterialConsumption">
        <h3>Raw Material Consumptions</h3>
        <Table border="1">
            <tr>
                <th>Raw Material</th>
                <th>Quantity</th>
            </tr>
            <xsl:apply-templates select="RawMaterial" />
        </Table>
    </xsl:template>

    <xsl:template match="//RealConsumptions/RawMaterial">
        <tr>
            <td>
                <xsl:value-of select="@id" />
            </td>
            <td>
                <xsl:value-of select="quantity" />
            </td>
        </tr>
    </xsl:template>

    <xsl:template name="ProductConsumption">
        <h3>Product Consumptions</h3>
        <Table border="1">
            <tr>
                <th>Product</th>
                <th>Quantity</th>
            </tr>
            <xsl:apply-templates select="Product" />
        </Table>
    </xsl:template>

    <xsl:template match="//RealConsumptions/Product">
        <tr>
            <td>
                <xsl:value-of select="@id" />
            </td>
            <td>
                <xsl:value-of select="quantity" />
            </td>
        </tr>
    </xsl:template>

    <xsl:template name="DepositConsumption">
        <table border="1">
            <h3>Deposit Consumptions</h3>
            <tr>
                <th>Deposit</th>
                <th>Quantity</th>
            </tr>
            <xsl:for-each select="Deposit">
                <tr>
                    <td>
                        <xsl:value-of select="@id" />
                    </td>
                    <td>
                        <xsl:value-of select="quantity" />
                    </td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>

    <xsl:template match="Machines">
        <h3>Machines</h3>
        <table border="1">
            <tr>
                <th>Internal Code</th>
                <th>State</th>
                <th>Serial Number</th>
                <th>Description</th>
                <th>Installation Date</th>
                <th>Brand</th>
                <th>Model</th>
                <th>Protocol</th>
                <th>Followed by Machine</th>
            </tr>
            <xsl:apply-templates select="Machine" />
        </table>

        <h3>Configuration Files</h3>
        <table border="1">
            <tr>
                <th>Machine</th>
                <th>Description</th>
            </tr>
            <xsl:apply-templates select="Machine/ConfigurationFiles/ConfigurationFile" />
        </table>
    </xsl:template>

    <xsl:template match="Machine">
        <tr>
            <td>
                <xsl:value-of select="@InternalCode" />
            </td>
            <td>
                <xsl:value-of select="@State" />
            </td>
            <td>
                <xsl:value-of select="SerialNumber" />
            </td>
            <td>
                <xsl:value-of select="Description" />
            </td>
            <td>
                <xsl:value-of select="InstallationDate" />
            </td>
            <td>
                <xsl:value-of select="Brand" />
            </td>
            <td>
                <xsl:value-of select="Model" />
            </td>
            <td>
                <xsl:value-of select="Protocol/@ID" />
            </td>
            <td>
                <xsl:value-of select="Machine/@InternalCode" />
            </td>
        </tr>
    </xsl:template>


    <xsl:template match="Machine/ConfigurationFiles/ConfigurationFile">
        <tr>
            <td>
                <xsl:value-of select="../../@InternalCode" />
            </td>
            <td>
                <xsl:value-of select="." />
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="Wastes">
        <h3>Wastes</h3>
        <table border="1">
            <tr>
                <th>Production Order</th>
                <th>Machine</th>
                <th>Item</th>
                <th>Quantity</th>
                <th>Deposit</th>
            </tr>
            <xsl:apply-templates select="Waste" />
        </table>
    </xsl:template>

    <xsl:template match="Waste">
        <tr>
            <td>
                <xsl:value-of select="./ProductionOrder/@ID" />
            </td>
            <td>
                <xsl:value-of select="./Machine/@InternalCode" />
            </td>
            <td>
                <xsl:if test="./Product">
                    <xsl:value-of select="./Product/@ID" />
                </xsl:if>
                <xsl:if test="./RawMaterial">
                    <xsl:value-of select="./RawMaterial/@ID" />
                </xsl:if>
            </td>
            <td>
                <xsl:value-of select="@quantity" />
            </td>
            <td>
                <xsl:value-of select="./Deposit/@ID" />
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="EffectiveTimes">
        <h3>Effective Times</h3>
        <table border="1">
            <tr>
                <th>Production Order</th>
                <th>Machine</th>
                <th>Minutes</th>
                <th>Seconds</th>
            </tr>
            <xsl:apply-templates select="EffectiveTime" />
        </table>
    </xsl:template>

    <xsl:template match="EffectiveTime">
        <tr>
            <td>
                <xsl:value-of select="./ProductionOrder/@ID" />
            </td>
            <td>
                <xsl:value-of select="./Machine/@InternalCode" />
            </td>
            <td>
                <xsl:value-of select="./@Minutes" />
            </td>
            <td>
                <xsl:value-of select="./@Seconds" />
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="BruteTimes">
        <h3>Brute Times</h3>
        <table border="1">
            <tr>
                <th>Production Order</th>
                <th>Machine</th>
                <th>Minutes</th>
                <th>Seconds</th>
            </tr>
            <xsl:apply-templates select="BruteTime" />
        </table>
    </xsl:template>

    <xsl:template match="BruteTime">
        <tr>
            <td>
                <xsl:value-of select="./ProductionOrder/@ID" />
            </td>
            <td>
                <xsl:value-of select="./Machine/@InternalCode" />
            </td>
            <td>
                <xsl:value-of select="./@Minutes" />
            </td>
            <td>
                <xsl:value-of select="./@Seconds" />
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>