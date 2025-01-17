-- Tạo cơ sở dữ liệu
USE master;
GO

DROP DATABASE IF EXISTS scoremanagement;
CREATE DATABASE scoremanagement;
GO

USE scoremanagement;
GO

-- Bảng Khoa (Faculty)
DROP TABLE IF EXISTS faculty;
CREATE TABLE faculty (
    IDFac VARCHAR(5) PRIMARY KEY,       
    NameFac NVARCHAR(50) NOT NULL      
);
GO

-- Bảng Lớp (Class)
DROP TABLE IF EXISTS class;
CREATE TABLE class (
    IDClass VARCHAR(10) PRIMARY KEY,   
    NameClass NVARCHAR(50) NOT NULL,  
    IDFac VARCHAR(5),                 
    FOREIGN KEY (IDFac) REFERENCES faculty(IDFac)

);
GO

-- Bảng Sinh Viên (Student)
DROP TABLE IF EXISTS student;
CREATE TABLE student (
    IDStu VARCHAR(5) PRIMARY KEY,    
    NameStu NVARCHAR(50) NOT NULL,   
    Gender BIT NOT NULL,             -- Giới tính (1 = Nam, 0 = Nữ)
    Birth DATE NOT NULL,             
    Home_Address NVARCHAR(100),      
    IDClass VARCHAR(10),              
    FOREIGN KEY (IDClass) REFERENCES class(IDClass)ON DELETE SET NULL
);
GO

-- Bảng Môn Học (Subject)
DROP TABLE IF EXISTS subject;
CREATE TABLE subject (
    IDSub VARCHAR(10) PRIMARY KEY,   
    NameSub NVARCHAR(100) NOT NULL, 
        
);
GO
--Subject
ALTER TABLE subject ADD NumCredits INT NOT NULL DEFAULT 0; 
go
ALTER TABLE subject ADD sub_status BIT DEFAULT 1;
go

-- Cập nhật các môn học hiện có để đảm bảo tất cả đều có trạng thái `1`
UPDATE subject SET sub_status = 1;
go
-- Bảng Điểm Thi (Score)
DROP TABLE IF EXISTS score;
CREATE TABLE score (
    IDStu VARCHAR(5),               
    IDSub VARCHAR(10),               
    Sc1 DECIMAL(4, 2),
    Sc2 DECIMAL(4, 2),
    Sc3 DECIMAL(4, 2),  
    Sctotal DECIMAL(4, 2),       
    Rate NVARCHAR(10),           
    PRIMARY KEY (IDStu, IDSub),    
    FOREIGN KEY (IDStu) REFERENCES student(IDStu)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (IDSub) REFERENCES subject(IDSub)
        ON DELETE CASCADE ON UPDATE CASCADE
);
GO

-- trigger 
DROP TRIGGER IF EXISTS trg_UpdateScore;
GO
CREATE TRIGGER trg_UpdateScore
ON score
AFTER INSERT, UPDATE
AS
BEGIN
    UPDATE score
    SET 
        Sctotal = Sc1 * 0.3 + Sc2 * 0.3 + Sc3 * 0.4,
        Rate = CASE 
            WHEN (Sc1 * 0.3 + Sc2 * 0.3 + Sc3 * 0.4) >= 8 THEN N'A'
            WHEN (Sc1 * 0.3 + Sc2 * 0.3 + Sc3 * 0.4) >= 6.5 THEN N'B'
            WHEN (Sc1 * 0.3 + Sc2 * 0.3 + Sc3 * 0.4) >= 5 THEN N'C'
            ELSE N'D'
        END
    FROM score
    WHERE score.IDStu IN (SELECT IDStu FROM inserted) AND score.IDSub IN (SELECT IDSub FROM inserted);
END;
GO


-- Bảng Tài Khoản (Login)
DROP TABLE IF EXISTS login;
CREATE TABLE login (
    ID VARCHAR(10) PRIMARY KEY,            
    Username VARCHAR(10),  
    Pass NVARCHAR(255) DEFAULT '1',         
    JobRole NVARCHAR(20) ,       
    IDStu VARCHAR(5),         
    FOREIGN KEY (IDStu) REFERENCES student(IDStu)ON DELETE SET NULL
);
GO


-- Thêm dữ liệu mẫu vào bảng Faculty
INSERT INTO faculty (IDFac, NameFac)
VALUES
('F01', N'Công Nghệ Thông Tin'),
('F02', N'Kinh Tế'),
('F03', N'Kỹ Thuật'),
('F04', N'Công Nghệ Sinh Học'),
('F05', N'Truyền Thông');



-- Thêm dữ liệu mẫu vào bảng Class
INSERT INTO class (IDClass, NameClass, IDFac)
VALUES
('C01', N'Công Nghệ Thông Tin 01', 'F01'),
('C02', N'Công Nghệ Thông Tin 02', 'F01'),
('C03', N'Kinh Tế 01', 'F02'),
('C04', N'Kinh Tế 02', 'F02'),
('C05', N'Kỹ Thuật 01', 'F03'),
('C06', N'Kỹ Thuật 02', 'F03'),
('C07', N'Công Nghệ Sinh Học 01', 'F04'),
('C08', N'Công Nghệ Sinh Học 02', 'F04'),
('C09', N'Truyền Thông 01', 'F05'),
('C10', N'Truyền Thông 02', 'F05'),
('C11', N'Công Nghệ Thông Tin 03', 'F01'),
('C12', N'Kinh Tế 03', 'F02'),
('C13', N'Kỹ Thuật 03', 'F03'),
('C14', N'Truyền Thông 03', 'F05');



-- Thêm dữ liệu mẫu vào bảng Student

INSERT INTO student (IDStu, NameStu, Gender, Birth, Home_Address, IDClass)
VALUES
('SV001', N'Nguyễn Văn A', 1, '2000-01-15', N'Hà Nội', 'C01'),
('SV002', N'Nguyễn Thị B', 0, '2001-03-22', N'Hồ Chí Minh', 'C02'),
('SV003', N'Phạm Văn C', 1, '1999-07-09', N'Đà Nẵng', 'C03'),
('SV004', N'Trần Thị D', 0, '2002-05-12', N'Hải Phòng', 'C04'),
('SV005', N'Lê Văn E', 1, '2000-09-18', N'Cần Thơ', 'C05'),
('SV006', N'Hoàng Thị F', 0, '2001-12-01', N'Nha Trang', 'C06'),
('SV007', N'Vũ Văn G', 1, '2002-04-21', N'Hạ Long', 'C07'),
('SV008', N'Đặng Thị H', 0, '1999-11-03', N'Quảng Ninh', 'C08'),
('SV009', N'Bùi Văn I', 1, '2000-08-19', N'Lào Cai', 'C09'),
('SV010', N'Đỗ Thị K', 0, '2001-06-13', N'Yên Bái', 'C10'),
('SV011', N'Ngô Văn L', 1, '2000-02-28', N'Nam Định', 'C11'),
('SV012', N'Tô Thị M', 0, '2002-10-15', N'Ninh Bình', 'C12'),
('SV013', N'Lý Văn N', 1, '2001-03-05', N'Thái Bình', 'C13'),
('SV014', N'Từ Thị O', 0, '2000-07-20', N'Hòa Bình', 'C14'),
('SV015', N'Châu Văn P', 1, '1999-09-15', N'Bắc Ninh', 'C01'),
('SV016', N'Thái Thị Q', 0, '2002-11-11', N'Vĩnh Phúc', 'C02'),
('SV017', N'Triệu Văn R', 1, '2001-01-25', N'Phú Thọ', 'C03'),
('SV018', N'Tống Thị S', 0, '1999-10-30', N'Quảng Trị', 'C04'),
('SV019', N'Kiều Văn T', 1, '2000-05-14', N'Quảng Nam', 'C05'),
('SV020', N'Phùng Thị U', 0, '2001-08-09', N'Thừa Thiên Huế', 'C06');

-- Thêm dữ liệu mẫu vào bảng Subject
INSERT INTO subject (IDSub, NameSub)
VALUES
('MH001', N'Toán Cao Cấp'),
('MH002', N'Lập Trình Cơ Bản'),
('MH003', N'Vật Lý Đại Cương'),
('MH004', N'Hóa Học Ứng Dụng'),
('MH005', N'Triết Học Mác-Lênin'),
('MH006', N'Kinh Tế Vi Mô'),
('MH007', N'Quản Lý Dự Án'),
('MH008', N'Phân Tích Dữ Liệu'),
('MH009', N'Trí Tuệ Nhân Tạo'),
('MH010', N'Cơ Sở Dữ Liệu');


-- Thêm dữ liệu mẫu vào bảng Score
INSERT INTO score (IDStu, IDSub, Sc1, Sc2, Sc3)
VALUES
('SV001', 'MH001', 8.5, 7.5, 9.0),
('SV002', 'MH002', 7.0, 6.5, 8.0),
('SV003', 'MH001', 9.0, 8.5, 9.5),
('SV004', 'MH003', 6.5, 7.0, 8.0),
('SV005', 'MH004', 5.0, 5.5, 6.0),
('SV006', 'MH005', 7.5, 8.0, 8.5),
('SV007', 'MH002', 9.0, 9.5, 10.0),
('SV008', 'MH006', 6.0, 6.5, 7.0),
('SV009', 'MH007', 8.0, 7.5, 8.5),
('SV010', 'MH008', 7.5, 7.0, 8.0),
('SV011', 'MH009', 9.0, 8.5, 9.0),
('SV012', 'MH010', 6.5, 6.0, 7.0),
('SV013', 'MH001', 7.0, 7.5, 8.0),
('SV014', 'MH002', 8.5, 9.0, 9.5);



-- Thêm dữ liệu mẫu vào bảng Login
----------------------------------------------LOGIN
SELECT * FROM login;
GO
go
-- Thêm dữ liệu mẫu vào bảng login
INSERT INTO login (ID, Username, Pass, JobRole, IDStu)
VALUES
('L01', 'SV001', '1', N'User', 'SV001'),
('L02', 'SV002', '1', N'User', 'SV002'),
('L03', 'SV003', '1', N'User', 'SV003'),
('L04', 'SV004', '1', N'User', 'SV004'),
('L05', 'SV005', '1', N'User', 'SV005'),
('L06', 'SV006', '1', N'User', 'SV006'),
('L07', 'SV007', '1', N'User', 'SV007'),
('L08', 'SV008', '1', N'User', 'SV008'),
('L09', 'SV009', '1', N'User', 'SV009'),
('L10', 'SV010', '1', N'User', 'SV010'),
('L11', 'SV011', '1', N'User', 'SV011'),
('L12', 'SV012', '1', N'User', 'SV012'),
('L13', 'SV013', '1', N'User', 'SV013'),
('L14', 'SV014', '1', N'User', 'SV014')

select *from student
SELECT JobRole
FROM login
WHERE Username = 'SV005' AND Pass = N'1';
GO
INSERT INTO login (ID, Username, Pass, JobRole, IDStu)
VALUES ('admin','1', N'1', N'Admin', null);
GO
go
CREATE OR ALTER PROCEDURE InsertStudentAndScore
    @IDStu VARCHAR(10),
    @NameStu NVARCHAR(50),
    @Gender BIT,
    @Birth DATE,
    @Home_Address NVARCHAR(100),
    @IDClass VARCHAR(10),
    @IDSub VARCHAR(10),
    @Sc1 DECIMAL(4, 2),
    @Sc2 DECIMAL(4, 2),
    @Sc3 DECIMAL(4, 2)

AS
BEGIN
    -- Kiểm tra xem IDClass có tồn tại trong bảng class không
    IF NOT EXISTS (SELECT 1 FROM class WHERE IDClass = @IDClass)
    BEGIN
        PRINT 'IDClass không tồn tại trong bảng class';
        RETURN;
    END

    -- Kiểm tra xem sinh viên đã tồn tại chưa
    IF EXISTS (SELECT 1 FROM student WHERE IDStu = @IDStu)
    BEGIN
        PRINT 'IDStu đã tồn tại trong bảng student';
    END
    ELSE
    BEGIN
        -- Thêm dữ liệu vào bảng student
        INSERT INTO student (IDStu, NameStu, Gender, Birth, Home_Address, IDClass)
        VALUES (@IDStu, @NameStu, @Gender, @Birth, @Home_Address, @IDClass);
    END

    -- Kiểm tra xem điểm của sinh viên đã tồn tại chưa
    IF EXISTS (SELECT 1 FROM score WHERE IDStu = @IDStu AND IDSub = @IDSub)
    BEGIN
        PRINT 'Điểm của sinh viên với môn học này đã tồn tại trong bảng score';
    END
    ELSE
    BEGIN
        -- Thêm dữ liệu vào bảng score
        INSERT INTO score (IDStu, IDSub, Sc1, Sc2, Sc3)
        VALUES (@IDStu, @IDSub, @Sc1, @Sc2, @Sc3);
    END
END;
GO

CREATE OR ALTER PROCEDURE DeleteStudent
    @IDStu VARCHAR(10)
AS
BEGIN
    -- Kiểm tra xem học sinh có tồn tại không
    IF NOT EXISTS (SELECT 1 FROM student WHERE IDStu = @IDStu)
    BEGIN
        PRINT 'Học sinh không tồn tại';
        RETURN;
    END

    -- Xóa dữ liệu trong bảng student
    DELETE FROM student
    WHERE IDStu = @IDStu;

    PRINT 'Học sinh đã được xóa thành công';
END;
GO

go
CREATE OR ALTER PROCEDURE SelectStudent
AS
BEGIN
    SELECT *
FROM 
    student s
END;
GO
exec SelectStudent
go
CREATE or ALTER PROCEDURE insertStu
    @IDStu VARCHAR(10),
    @NameStu NVARCHAR(50),
	@Home_Address NVARCHAR(100),
	@Birth DATE,
    @Gender BIT,
    @IDClass VARCHAR(10)
AS
BEGIN
    -- Kiểm tra xem ID lớp có tồn tại trong bảng class hay không
    IF EXISTS (SELECT 1 FROM class WHERE IDClass = @IDClass)
    BEGIN
        -- Thực hiện chèn dữ liệu vào bảng student
        INSERT INTO student (IDStu, NameStu, Home_Address, Birth, Gender, IDClass)
        VALUES (@IDStu, @NameStu, @Home_Address, @Birth, @Gender, @IDClass);
    END
END;
GO

create or alter procedure deleteStu
@id VARCHAR(10)
as 
begin 
	delete from student where IDStu=@id
end 
go

CREATE OR ALTER PROCEDURE insertFaculty
    @IDFac VARCHAR(5),
    @NameFac NVARCHAR(50)
AS
BEGIN

    INSERT INTO faculty (IDFac, NameFac)
    VALUES (@IDFac, @NameFac);
END;
GO
create or alter procedure updateStu
    @NameStu NVARCHAR(50),
	@Home_Address NVARCHAR(100),
	@Birth DATE,
    @Gender BIT,
    @IDClass VARCHAR(10),
	@IDStu VARCHAR(10)
as 
begin 
	Update student
	set NameStu=@NameStu,
	Home_Address=@Home_Address,
	Birth=@Birth,
	Gender=@Gender,
	IDClass=@IDClass
	where IDStu=@IDStu
end 
go
EXEC updateStu 
    @NameStu = N'Nguyen Van C',
    @Home_Address = N'789 GHI Street',
    @Birth = ' 2000-01-01',
    @Gender = 0,
    @IDClass = 'c01',
    @IDStu = '2000123';


go

CREATE OR ALTER PROCEDURE insertFaculty
    @IDFac VARCHAR(5),
    @NameFac NVARCHAR(50)
AS
BEGIN

    INSERT INTO faculty (IDFac, NameFac)
    VALUES (@IDFac, @NameFac);
END;
GO
CREATE OR ALTER PROCEDURE deleteFaculty
    @id NVARCHAR(10) 
AS
BEGIN
    DELETE FROM Faculty WHERE IDFac = @id;
END;
go
CREATE OR ALTER PROCEDURE UpdateFaculty
    @IDFac VARCHAR(5),       
    @NameFac NVARCHAR(50)    
AS
BEGIN
    -- Kiểm tra xem ID khoa có tồn tại không
    IF NOT EXISTS (SELECT 1 FROM faculty WHERE IDFac = @IDFac)
    BEGIN
        PRINT 'Faculty ID does not exist.';
        RETURN;
    END

    -- Cập nhật thông tin khoa
    UPDATE faculty
    SET NameFac = @NameFac
    WHERE IDFac = @IDFac;

    PRINT 'Faculty updated successfully.';
END;
GO
CREATE OR ALTER PROCEDURE insertClass (
    @IDclass VARCHAR(50),
    @NameClass NVARCHAR(100),
    @IDFac VARCHAR(50)
)
AS
BEGIN
    INSERT INTO class (IDclass, NameClass, IDFac)
    VALUES (@IDclass, @NameClass, @IDFac);
END;
GO
CREATE OR ALTER PROCEDURE UpdateClass
    @IDClass VARCHAR(10),          
    @NameClass NVARCHAR(100),      
    @IDFac VARCHAR(10)             
AS
BEGIN
   
    IF NOT EXISTS (SELECT 1 FROM class WHERE IDClass = @IDClass)
    BEGIN
        PRINT 'Class ID does not exist.';
        RETURN;
    END

    UPDATE class
    SET NameClass = @NameClass, IDFac = @IDFac
    WHERE IDClass = @IDClass;

    PRINT 'Class updated successfully.';
END;
GO
CREATE OR ALTER PROCEDURE deleteClass
    @IDClass VARCHAR(10)
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM class WHERE IDClass = @IDClass)
    BEGIN
        PRINT 'Class ID does not exist.';
        RETURN;
    END

    -- Xóa lớp học
    DELETE FROM class
    WHERE IDClass = @IDClass;

    PRINT 'Class deleted successfully.';
END;
go
CREATE OR ALTER PROCEDURE AllScores
AS
BEGIN
    SELECT 
        sc.IDStu,
        stu.NameStu,
		sub.IDSub,
        sub.NameSub,
        sc.Sc1,
        sc.Sc2,
        sc.Sc3,
        sc.Sctotal,
        sc.Rate
    FROM 
        score sc
    JOIN 
        student stu ON sc.IDStu = stu.IDStu
    JOIN 
        subject sub ON sc.IDSub = sub.IDSub;
END;
GO

CREATE OR ALTER PROCEDURE UpdateScore
    @Sc1 DECIMAL(10, 2),
    @Sc2 DECIMAL(10, 2),
    @Sc3 DECIMAL(10, 2),
    @IDStu NVARCHAR(50),
    @IDSub NVARCHAR(50)
AS
BEGIN
    UPDATE score
    SET Sc1 = @Sc1,
        Sc2 = @Sc2,
        Sc3 = @Sc3
    WHERE IDStu = @IDStu AND IDSub = @IDSub;
END
GO
CREATE OR ALTER PROCEDURE InsertScore
    @IDStu VARCHAR(10),
    @IDSub VARCHAR(10),
    @Sc1 DECIMAL(10, 2),
    @Sc2 DECIMAL(10, 2),
    @Sc3 DECIMAL(10, 2)
AS
BEGIN
    -- Kiểm tra xem IDStu và IDSub đã có điểm hay chưa
    IF EXISTS (SELECT 1 FROM score WHERE IDStu = @IDStu AND IDSub = @IDSub AND (Sc1 IS NOT NULL OR Sc2 IS NOT NULL OR Sc3 IS NOT NULL))
    BEGIN
        PRINT 'Điểm theo IDStu và IDSub đã tồn tại!';
    END
    ELSE
    BEGIN
        INSERT INTO score (IDStu, IDSub, Sc1, Sc2, Sc3)
        VALUES (@IDStu, @IDSub, @Sc1, @Sc2, @Sc3);
    END;
END;
GO
--------------------SCORE
CREATE OR ALTER PROCEDURE DeleteScore(@IDStu VARCHAR(10), @IDSub VARCHAR(10))
AS
BEGIN
    -- Kiểm tra xem có điểm cho IDStu và IDSub hay không
    IF EXISTS (SELECT 1 FROM score WHERE IDStu = @IDStu AND IDSub = @IDSub AND (Sc1 IS NOT NULL OR Sc2 IS NOT NULL OR Sc3 IS NOT NULL))
    BEGIN
        DELETE FROM score WHERE IDStu = @IDStu AND IDSub = @IDSub;
    END
    
END;
GO
go
CREATE OR ALTER PROCEDURE validateUser
    @IDStu NVARCHAR(10)
AS
BEGIN
    SELECT DISTINCT 
        s.*,
        c.NameClass ,
        f.*
    FROM 
        login l
    JOIN 
        student s ON l.IDStu = s.IDStu
    JOIN 
        class c ON s.IDClass = c.IDClass
    JOIN 
        faculty f ON c.IDFac = f.IDFac
    WHERE 
        l.IDStu = @IDStu
END
go
CREATE OR ALTER PROCEDURE getScoresStudent
    @StudentId NVARCHAR(10)
AS
BEGIN
    SELECT 
		sub.IDSub,
        sub.NameSub,
        sc.Sc1,
        sc.Sc2,
        sc.Sc3,
        sc.Sctotal,
        sc.Rate
    FROM 
        score sc
    JOIN 
        subject sub ON sc.IDSub = sub.IDSub
    WHERE 
        sc.IDStu = @StudentId;
END
GO
--Subject
--ALTER TABLE subject ADD NumCredits INT NOT NULL DEFAULT 0; 
--ALTER TABLE subject ADD sub_status BIT DEFAULT 1;

-- Cập nhật các môn học hiện có để đảm bảo tất cả đều có trạng thái `1`
-- subject SET sub_status = 1;

go
CREATE OR ALTER PROCEDURE insertSubject (
    @IDSub VARCHAR(10),        -- Mã môn học
    @NameSub NVARCHAR(100),    -- Tên môn học
    @NumCredits INT            -- Số tín chỉ
)
AS
BEGIN
    INSERT INTO subject (IDSub, NameSub, NumCredits)
    VALUES (@IDSub, @NameSub, @NumCredits);
END;
GO

CREATE OR ALTER PROCEDURE updateSubject (
    @IDSub VARCHAR(10),       
    @NameSub NVARCHAR(100),   
    @NumCredits INT           
)
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM subject WHERE IDSub = @IDSub)
    BEGIN
        PRINT 'Subject ID does not exist.';
        RETURN;
    END

    -- Cập nhật thông tin môn học
    UPDATE subject
    SET 
        NameSub = @NameSub,
        NumCredits = @NumCredits
    WHERE 
        IDSub = @IDSub;

    PRINT 'Subject updated successfully.';
END;
GO
CREATE OR ALTER PROCEDURE deactivateSubject (
    @IDSub VARCHAR(10)
)
AS
BEGIN
    IF NOT EXISTS (SELECT 1 FROM subject WHERE IDSub = @IDSub)
    BEGIN
        PRINT 'Subject ID does not exist.';
        RETURN;
    END

    UPDATE subject
    SET sub_status = 0
    WHERE IDSub = @IDSub;

    PRINT 'Subject deactivated successfully.';
END;
GO


-----------------------TEST
select *from login
exec deleteStu 1
select nameFac from faculty
select NameSub from subject
select *from score
select * from student
select IDSub from subject
SELECT * FROM student WHERE IDStu LIKE 'sv%';

SELECT s.IDStu, s.NameStu , s.Gender, s.Birth, s.Home_Address, c.IDClass, c.NameClass
FROM student s, class c, faculty f 
WHERE s.IDClass = c.IDClass AND c.IDFac = f.IDFac;

SELECT 
    s.IDStu, 
    s.NameStu, 
    c.NameClass, 
    f.NameFac,
    sc.IDSub,
    sub.NameSub,
    sc.Sc1,
    sc.Sc2,
    sc.Sc3,
    sc.Sctotal,
    sc.Rate
FROM 
    student s
JOIN 
    class c ON s.IDClass = c.IDClass
JOIN 
    faculty f ON c.IDFac = f.IDFac
JOIN 
    score sc ON s.IDStu = sc.IDStu
JOIN 
    subject sub ON sc.IDSub = sub.IDSub;
	SELECT * FROM score

select *from class

select *from login
SELECT 
    stu.IDStu,
    stu.NameStu,
    sub.NameSub,
    sc.Sc1,
    sc.Sc2,
    sc.Sc3,
    sc.Sctotal,
    sc.Rate
FROM 
    student stu
LEFT JOIN 
    score sc ON stu.IDStu = sc.IDStu
LEFT JOIN 
    subject sub ON sc.IDSub = sub.IDSub;

	select *from score
exec DeleteScore @IDStu='111', @IDSub='MH002'
go
select *from faculty
select *from subject
SELECT *FROM CLASS
SELECT s.IDStu, stu.NameStu, s.IDSub, sub.NameSub , s.Sc1, s.Sc2, s.Sc3, s.Sctotal, s.Rate
FROM score s
JOIN subject sub ON s.IDSub = sub.IDSub
JOIN student stu ON s.IDStu = stu.IDStu;
GO
select*from student
DELETE FROM student WHERE IDStu = 'SV034';









