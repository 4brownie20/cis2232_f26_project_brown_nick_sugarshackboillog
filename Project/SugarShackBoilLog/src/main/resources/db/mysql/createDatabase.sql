# Sugar Shack Boil Log - database creation script
# Author: Nick Brown
# Since: 2026-09-25
# Fields specified by the BA (Kody Verhulp) in the project topic document.

#For localhost
DROP DATABASE IF EXISTS cis2232_sugar_shack_boil_log;
CREATE DATABASE cis2232_sugar_shack_boil_log;
use cis2232_sugar_shack_boil_log;

-- ------------------------------------------------------------------------------
-- The table below holds the details for each boil session.
-- Note: syrupProduced is needed by both calculations (pending BA confirmation
-- in the topic document).
-- ------------------------------------------------------------------------------

CREATE TABLE BoilLog
(
    boilId         int(5),
    boilDate       varchar(10) NOT NULL COMMENT 'yyyy-MM-dd',
    tapCount       int(5)      NOT NULL COMMENT 'Number of taps',
    sapVolume      double      NOT NULL COMMENT 'Litres of sap boiled',
    sapBrix        double      NOT NULL COMMENT 'Sugar content of the sap (Brix)',
    boilStartTime  varchar(5)  NOT NULL COMMENT 'hh:mm - time the fire was lit',
    boilEndTime    varchar(5)  NOT NULL COMMENT 'hh:mm - time the last batch was taken off',
    fuelType       varchar(10) NOT NULL COMMENT 'wood, propane, oil or electric',
    fuelQuantity   double      NOT NULL COMMENT 'Fuel burned (cords, litres or kWh based on fuelType)',
    syrupProduced  double      NOT NULL COMMENT 'Litres of finished syrup produced',
    boilEfficiency double COMMENT 'Calculated at submission - actual vs predicted yield (%)',
    costPerLitre   double COMMENT 'Calculated at submission - fuel cost per litre of syrup'
) COMMENT 'This table holds the boil session details for the sugar shack';

ALTER TABLE BoilLog
    ADD PRIMARY KEY (boilId);
ALTER TABLE BoilLog
    MODIFY boilId int(4) NOT NULL AUTO_INCREMENT COMMENT 'This is the primary key',
    AUTO_INCREMENT = 1;

INSERT INTO BoilLog (boilId, boilDate, tapCount, sapVolume, sapBrix, boilStartTime, boilEndTime,
                     fuelType, fuelQuantity, syrupProduced, boilEfficiency, costPerLitre)
VALUES
       (1, '2026-03-14', 150, 800, 2.0, '08:30', '15:45', 'wood', 0.5, 17.5, 94.06, 4.29),
       (2, '2026-03-18', 150, 950, 2.2, '09:00', '17:15', 'wood', 0.6, 23.0, 94.64, 3.91),
       (3, '2026-03-21', 160, 1100, 2.5, '07:45', '16:30', 'propane', 95, 30.5, 95.38, 2.59),
       (4, '2026-03-25', 160, 700, 1.8, '10:00', '15:00', 'oil', 40, 13.8, 94.19, 6.09),
       (5, '2026-03-29', 165, 1200, 2.4, '08:00', '18:00', 'electric', 210, 31.0, 92.57, 1.02),
       (6, '2026-04-02', 165, 650, 1.6, '11:00', '15:30', 'wood', 0.35, 9.0, 74.42, 5.83);



CREATE TABLE CodeType (codeTypeId int(3) COMMENT 'This is the primary key for code types',
                       englishDescription varchar(100) NOT NULL COMMENT 'English description',
                       frenchDescription varchar(100) DEFAULT NULL COMMENT 'French description',
                       createdDateTime datetime DEFAULT NULL,
                       createdUserId varchar(20) DEFAULT NULL,
                       updatedDateTime datetime DEFAULT NULL,
                       updatedUserId varchar(20) DEFAULT NULL
) COMMENT 'This tables holds the code types that are available for the application';

ALTER TABLE CodeType
    ADD PRIMARY KEY (CodeTypeId);

INSERT INTO CodeType (CodeTypeId, englishDescription, frenchDescription, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (1, 'User Types', 'Types d''utilisateurs', sysdate(), '', CURRENT_TIMESTAMP, '');
INSERT INTO CodeType (CodeTypeId, englishDescription, frenchDescription, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 'Fuel Types', 'Types de combustible', sysdate(), '', CURRENT_TIMESTAMP, '');



CREATE TABLE CodeValue (
                           codeTypeId int(3) NOT NULL COMMENT 'see code_type table',
                           codeValueSequence int(3) NOT NULL,
                           englishDescription varchar(100) NOT NULL COMMENT 'English description',
                           englishDescriptionShort varchar(20) NOT NULL COMMENT 'English abbreviation for description',
                           frenchDescription varchar(100) DEFAULT NULL COMMENT 'French description',
                           frenchDescriptionShort varchar(20) DEFAULT NULL COMMENT 'French abbreviation for description',
                           sortOrder int(3) DEFAULT NULL COMMENT 'Sort order if applicable',
                           createdDateTime datetime DEFAULT NULL,
                           createdUserId varchar(20) DEFAULT NULL,
                           updatedDateTime datetime DEFAULT NULL,
                           updatedUserId varchar(20) DEFAULT NULL
) COMMENT='This will hold code values for the application.';

ALTER TABLE CodeValue
    ADD PRIMARY KEY (CodeTypeId, codeValueSequence);

INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (1, 1, 'General', 'General', 'Général', 'Général', '2026-09-25 12:00:00', 'admin', '2026-09-25 12:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (1, 2, 'Admin', 'Admin', 'Administrateur', 'Admin', '2026-09-25 12:00:00', 'admin', '2026-09-25 12:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 1, 'Wood (cord)', 'wood', 'Bois (corde)', 'bois', '2026-09-25 12:00:00', 'admin', '2026-09-25 12:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 2, 'Propane (litre)', 'propane', 'Propane (litre)', 'propane', '2026-09-25 12:00:00', 'admin', '2026-09-25 12:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 3, 'Oil (litre)', 'oil', 'Mazout (litre)', 'mazout', '2026-09-25 12:00:00', 'admin', '2026-09-25 12:00:00', 'admin');
INSERT INTO CodeValue (codeTypeId, codeValueSequence, englishDescription, englishDescriptionShort, frenchDescription, frenchDescriptionShort, createdDateTime, createdUserId, updatedDateTime, updatedUserId) VALUES
    (2, 4, 'Electric (kWh)', 'electric', 'Électrique (kWh)', 'électrique', '2026-09-25 12:00:00', 'admin', '2026-09-25 12:00:00', 'admin');
